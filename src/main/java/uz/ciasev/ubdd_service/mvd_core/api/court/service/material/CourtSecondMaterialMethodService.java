package uz.ciasev.ubdd_service.mvd_core.api.court.service.material;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.MaterialHelpCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.material.CourtMaterialRegistrationRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.court.CourtDecisionNotFoundException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.court.DuplicateCourtRequestException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtSecondMaterialMethodService {

    private final AdmCaseService admCaseService;
    private final ResolutionActionService resolutionActionService;
    private final AdmCaseStatusService admStatusService;
    private final CourtMaterialFieldsService fieldsService;
    private final AdmEventService admEventService;
    private final MaterialHelpCourtService materialHelpCourtService;
    private final CourtMaterialService materialService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
    private final ResolutionService resolutionService;
    private final DecisionService decisionService;


    @Transactional
    public CourtMaterial accept(CourtMaterialRegistrationRequestDTO registration) {
        materialHelpCourtService.checkIgnoredMaterialType(registration.getMaterialType());

        Optional<CourtMaterial> existsMaterial = materialService.findByClaimId(registration.getClaimId());
        if (existsMaterial.isPresent()) {
            return existsMaterial.get();
        }

        try {
            courtDuplicateRequestService.checkAndRemember(registration);
        } catch (DuplicateCourtRequestException e) {
            //  Cуд в методе регистрации в envelopedId ждет id материала.
            throw new CourtValidationException("Not found material for duplicate request");
        }

        return registerNewMaterial(registration);
    }

    private CourtMaterial registerNewMaterial(CourtMaterialRegistrationRequestDTO registration) {

        Function<CourtMaterialRegistrationRequestDTO, CourtMaterial> consumer = calculateConsumer(registration);

        CourtMaterial material = consumer.apply(registration);
        admEventService.fireEvent(AdmEventType.REGISTERED_MATERIAL_IN_COURT, material);
        return material;
    }

    private CourtMaterial acceptMaterialFor315(CourtMaterialRegistrationRequestDTO registration) {
        AdmCase admCase = admCaseService.getById(registration.getCaseId());

        Decision requestedDecision = materialHelpCourtService.findDecisionByCourtSearchParam(registration.getResolutionSeries(), registration.getResolutionNumber())
                .orElseThrow(() -> new CourtDecisionNotFoundException(registration.getResolutionSeries(), registration.getResolutionNumber()));

        validate315(admCase, requestedDecision, registration);

        Decision decision = prepareDecisionForMaterialBase(admCase, requestedDecision);
        resolutionActionService.review(List.of(decision));

        CourtMaterial material = materialService.create(registration.getClaimId(), decision);
        fieldsService.open(material, registration);


        //  todo 💩
        admCase.setCourtConsideringBasis(new CourtConsideringBasis(-1L));
        admStatusService.setStatus(admCase, AdmStatusAlias.SENT_TO_COURT);
        admCaseService.update(admCase.getId(), admCase);

        return material;
    }

    void validate315(AdmCase admCase, Decision decision, CourtMaterialRegistrationRequestDTO registration) {
        if (registration.getPersonsId().size() != 1) {
            throw new CourtValidationException(MATERIAL_VIOLATOR_NOT_SINGLE);
        }

        Long personId = registration.getPersonsId().get(0);
        if (!decision.getViolator().getPersonId().equals(personId)) {
            throw new CourtValidationException(NOT_CONSISTENT_VIOLATOR_ID);
        }

        if (!admCase.getId().equals(decision.getResolution().getAdmCaseId())) {
            throw new CourtValidationException(NOT_CONSISTENT_ADM_CASE_ID);
        }
    }

    private CourtMaterial acceptMaterialForCourtResolution(CourtMaterialRegistrationRequestDTO registration) {
        throw new CourtValidationException("Material for court resolution not implemented yet");
    }

    private Function<CourtMaterialRegistrationRequestDTO, CourtMaterial> calculateConsumer(CourtMaterialRegistrationRequestDTO registration) {
//        Для решений миба серия пустая.
//        if (registration.getMaterialPreviousClaimId() == null && (registration.getResolutionNumber() == null || registration.getResolutionSeries() == null)) {
        if (registration.getMaterialPreviousClaimId() == null && (registration.getResolutionNumber() == null)) {
            throw new CourtValidationException(MATERIAL_BASE_REQUIRED);
        }

        if (registration.getMaterialPreviousClaimId() != null && (registration.getResolutionNumber() != null)) {
            throw new CourtValidationException(MATERIAL_BASE_AMBIGUOUS);
        }

        return registration.getMaterialPreviousClaimId() == null
                ? this::acceptMaterialFor315
                : this::acceptMaterialForCourtResolution;
    }


    private Decision prepareDecisionForMaterialBase(AdmCase admCase, Decision decision) {
        if (decision.getResolution().isActive()) return decision;

        Optional<Resolution> currentResolutionOpt = resolutionService.findActiveByAdmCaseId(admCase.getId());
        if (currentResolutionOpt.isEmpty()) {
            resolutionActionService.restore(List.of(decision));
            admStatusService.setStatus(admCase, AdmStatusAlias.DECISION_MADE);
            // TODO: 06.11.2023
            return decision;
        }

        Resolution currentResolution = currentResolutionOpt.get();
        if (currentResolution.getOrgan().isCourt()) {
            throw new CourtValidationException(MATERIAL_BASE_NOT_ACTIVE_AND_CASE_ALREADY_HAS_COURT_RESOLUTION);
        }

        return decisionService.getByResolutionAndViolatorIds(currentResolution.getId(), decision.getViolatorId());
//        throw new CourtValidationException(MATERIAL_BASE_NOT_ACTIVE);
        // todo реализовать отмену текущего решения и востановление того, а каторый пршел материал.
    }

}
