package uz.ciasev.ubdd_service.service.main.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.CriminalCaseDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.CriminalCaseResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.TransferToCriminalCaseRequestDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.service.generator.ValueNumberGeneratorService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.user.InspectorService;
import uz.ciasev.ubdd_service.service.user.UserService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.CREATE_TRANSFER_TO_CRIMINAL_CASE_RESOLUTION;

@Service
@RequiredArgsConstructor
public class UserTransferToCriminalCaseResolutionServiceImpl implements UserTransferToCriminalCaseResolutionService {
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final ResolutionHelpService helpService;
    private final DictionaryService<Organ> organService;
    private final ProtocolService protocolService;
    private final ResolutionService resolutionService;
    private final ViolatorService violatorService;
    private final InspectorService inspectorService;
    private final AdmEventService notificatorService;
    private final UserService userService;


    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.RESOLUTION)
    @Override
    public CreatedResolutionDTO create(User user, Long admCaseId, TransferToCriminalCaseRequestDTO requestDTO) {

        AdmCase admCase = admCaseService.getById(admCaseId);
        validate(user, admCase, requestDTO);

        Organ organ = calculateResolutionOrgan(user, requestDTO);
        Place resolutionPlace = calculateResolutionPlace(requestDTO, organ);
        Inspector inspector = inspectorService.buildCriminalInvestigatorInspector(user, organ, requestDTO);

        //  собираем сущности
        ResolutionCreateRequest resolution = helpService.buildResolution(buildResolutionDTO(requestDTO));
        List<Compensation> emptyList = List.of();
        List<Pair<Decision, List<Compensation>>> decisions = violatorService.findByAdmCaseId(admCaseId)
                .stream()
                .map(violator -> {
                    Decision decision = helpService.buildDecision(violator, buildDecisionDTO(requestDTO), null);
                    decision.setIsSavedPdf(true);

                    return decision;
                })
                .map(d -> Pair.of(d, emptyList))
                .collect(Collectors.toList());

        // начиныем сохранять решение
        admCase.setConsiderUser(user);
        admCase.setConsiderInfo(user.getInfo());


        CreatedResolutionDTO data = helpService.resolve(
//                AdmEventType.ORGAN_RESOLUTION_CREATE,
                admCase,
                inspector,
                resolutionPlace,
                new ValueNumberGeneratorService(requestDTO.getResolutionNumber(), false),
                new ValueNumberGeneratorService(requestDTO.getResolutionNumber(), false),
                resolution,
                decisions,
                List.of()
        );

        Resolution savedResolution = data.getResolution();

        notificatorService.fireEvent(AdmEventType.ORGAN_RESOLUTION_CREATE, data);

        return data;
    }

    private Place calculateResolutionPlace(TransferToCriminalCaseRequestDTO requestDTO, Organ organ) {
        return new Place() {
            @Override
            public Region getRegion() {
                return requestDTO.getRegion();
            }

            @Override
            public District getDistrict() {
                return requestDTO.getDistrict();
            }

            @Override
            public Organ getOrgan() {
                return organ;
            }

            @Override
            public Department getDepartment() {
                return null;
            }
        };
    }

    private void validate(User user, AdmCase admCase, TransferToCriminalCaseRequestDTO requestDTO) {
        admCaseAccessService.checkConsiderActionWithAdmCase(user, CREATE_TRANSFER_TO_CRIMINAL_CASE_RESOLUTION, admCase);
        validateResolutionDate(admCase, requestDTO);
        validateResolutionNumber(requestDTO);
    }

    private CriminalCaseResolutionRequestDTO buildDTO(User user, AdmCase admCase, TransferToCriminalCaseRequestDTO requestDTO) {
        List<Violator> violators = violatorService.findByAdmCaseId(admCase.getId());

        List<CriminalCaseDecisionRequestDTO> decisionsDTO = violators.stream()
                .map(violator -> {
                    CriminalCaseDecisionRequestDTO decisionDTO = new CriminalCaseDecisionRequestDTO();
                    decisionDTO.setViolatorId(violator.getId());
                    decisionDTO.setDecisionType(DecisionTypeAlias.TRANSFER_TO_CRIMINAL_CASE);
                    decisionDTO.setCriminalCaseTransferResultType(requestDTO.getTransferResultType());
                    decisionDTO.setExecutionFromDate(requestDTO.getResolutionDate());
                    return decisionDTO;
                })
                .collect(Collectors.toList());

        CriminalCaseResolutionRequestDTO resolutionDTO = new CriminalCaseResolutionRequestDTO();
        resolutionDTO.setResolutionTime(requestDTO.getResolutionDate().atStartOfDay());
        resolutionDTO.setFileUri(requestDTO.getFileUri());
        resolutionDTO.setDecisions(decisionsDTO);

        return resolutionDTO;
    }

    private CriminalCaseDecisionRequestDTO buildDecisionDTO(TransferToCriminalCaseRequestDTO requestDTO) {
        CriminalCaseDecisionRequestDTO decisionDTO = new CriminalCaseDecisionRequestDTO();
        decisionDTO.setDecisionType(DecisionTypeAlias.TRANSFER_TO_CRIMINAL_CASE);
        decisionDTO.setCriminalCaseTransferResultType(requestDTO.getTransferResultType());
        decisionDTO.setExecutionFromDate(requestDTO.getResolutionDate());
        return decisionDTO;
    }

    private CriminalCaseResolutionRequestDTO buildResolutionDTO(TransferToCriminalCaseRequestDTO requestDTO) {
        CriminalCaseResolutionRequestDTO resolutionDTO = new CriminalCaseResolutionRequestDTO();
        resolutionDTO.setResolutionTime(requestDTO.getResolutionDate().atStartOfDay());
        resolutionDTO.setFileUri(requestDTO.getFileUri());
        return resolutionDTO;
    }

    private Organ calculateResolutionOrgan(User user, TransferToCriminalCaseRequestDTO requestDTO) {
        if (requestDTO.getOrgan() == null) {
            Organ userOrgan = userService.getUserRelateOrgan(user);
            if (userOrgan.getCriminalInvestigatingDepartmentOrganId() == null) {
                throw new ValidationException(ErrorCode.USER_ORGAN_HAS_NO_CRIMINAL_INVESTIGATING_DEPARTMENT);
            }

            return organService.getById(user.getOrgan().getCriminalInvestigatingDepartmentOrganId());

        } else {
            if (!requestDTO.getOrgan().getIsGlobalCriminalInvestigator()) {
                throw new ValidationException(ErrorCode.RESOLUTION_ORGAN_IS_NOT_GLOBAL_CRIMINAL_INVESTIGATOR);
            }

            return requestDTO.getOrgan();
        }
    }

    private void validateResolutionDate(AdmCase admCase, TransferToCriminalCaseRequestDTO requestDTO) {
        LocalDate resolutionDate = requestDTO.getResolutionDate();
        Protocol protocol = protocolService.findEarliestProtocolInAdmCase(admCase.getId());
        LocalDate protocolDate = protocol.getCreatedTime().toLocalDate();

        if (resolutionDate.isBefore(protocolDate)) {
            throw new ValidationException(ErrorCode.RESOLUTION_DATE_BEFORE_FIRST_PROTOCOL_DATE);
        }
    }

    private void validateResolutionNumber(TransferToCriminalCaseRequestDTO requestDTO) {
        if (resolutionService.existsBySeriesAndNumber("", requestDTO.getResolutionNumber())) {
            throw new ValidationException(ErrorCode.RESOLUTION_WITH_SAME_NUMBER_EXISTS);
        }
    }
}
