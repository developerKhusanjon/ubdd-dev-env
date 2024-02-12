package uz.ciasev.ubdd_service.mvd_core.api.court.service.two;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtEventHolder;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CourtRequestOrderService;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.service.court.CourtCaseChancelleryDataRequest;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventCourtDataService;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtDeclineReasonsHistoryService;
import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.dict.court.CourtDeclineReasonService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

import java.util.EnumSet;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.CANCELLED;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.REGISTERED_IN_COURT;
import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecondMethodFromCourtServiceImpl implements SecondMethodFromCourtService {

    private static final EnumSet<CourtStatusAlias> registrationStatuses = EnumSet.of(CANCELLED, REGISTERED_IN_COURT);

    private final AdmCaseService admCaseService;
    private final AdmCaseStatusService admStatusService;
    private final CourtCaseFieldsService courtCaseFieldsService;
    private final CourtAdmCaseMovementService courtCaseMovementService;
    private final CourtDeclineReasonService courtDeclineReasonService;
    private final CourtDeclineReasonsHistoryService courtDeclineReasonsHistoryService;
    private final CourtEventHolder courtEventHolder;
    private final PublicApiWebhookEventCourtDataService eventDataService;
    private final PublicApiWebhookEventPopulationService webhookEventPopulationService;
    private final AdmEventService notificatorService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
    private final CourtRequestOrderService orderService;

    @Override
    @Transactional
    public void accept(CourtRegistrationStatusRequestDTO registration) {
        courtDuplicateRequestService.checkAndRemember(registration);

        orderService.applyWithOrderCheck(CourtMethod.COURT_SECOND, registration, this::handle);
    }

    private void handle(CourtRegistrationStatusRequestDTO registration) {
        validateSecondMethod(registration);


        AdmCase admCase = admCaseService.getById(registration.getCaseId());
        saveHistory(admCase, registration);
        CourtCaseChancelleryData courtCaseFields = courtCaseFieldsService.saveChancelleryData(buildCourtCaseFields(registration, admCase));

        boolean isHandleNotInterrupted = handleCase(admCase, registration);
        if (!isHandleNotInterrupted) {
            return;
        }

        // Оповещения для public-api
        courtEventHolder.init();
        eventDataService.setCourtDTOForFields(courtEventHolder.getCurrentInstance(), courtCaseFields);
        webhookEventPopulationService.addCourtEvent(courtEventHolder.close());

        // Запуст постобработки события (смс, оповещения юзеров...)
        notificatorService.fireEvent(AdmEventType.ADM_CASE_REGISTRATION_STATUS_IN_COURT, courtCaseFields);
    }

    private boolean handleCase(AdmCase admCase, CourtRegistrationStatusRequestDTO registration) {
        if (courtCaseMovementService.hasThirdMethod(registration.getCaseId(), registration.getClaimId())) {
            return false;
        }

        if (admCase.getClaimId() == null) {
            admCase.setClaimId(registration.getClaimId());
        }

        CourtStatus courtStatus = registration.getStatus();

        if (!registration.getClaimId().equals(admCase.getClaimId())) {
            if (courtStatus.is(CANCELLED)) {
                return false;
            }

            if (!admCase.getStatus().oneOf(AdmStatusAlias.PREPARE_FOR_COURT, AdmStatusAlias.RETURN_FROM_COURT, AdmStatusAlias.SENT_TO_COURT)) {
                throw new CourtValidationException(String.format("Case status is %s and caseClaim != requestClaim (%s != %s)", admCase.getStatus().getAlias(), admCase.getClaimId(), registration.getClaimId()));
            }

            admStatusService.setStatus(admCase, AdmStatusAlias.SENT_TO_COURT);
            admCase.setClaimId(registration.getClaimId());
        }


        if (courtStatus.is(CANCELLED)) {
            admStatusService.setStatus(admCase, AdmStatusAlias.RETURN_FROM_COURT);
        }

        admCase.setCourtStatus(courtStatus);
        admCaseService.update(admCase.getId(), admCase);

        return true;
    }

    private void saveHistory(AdmCase admCase, CourtRegistrationStatusRequestDTO registration) {
        courtCaseMovementService.save(registration);
        if (registration.getStatus().is(CANCELLED)) {
            courtDeclineReasonsHistoryService.save(admCase, registration);
        }
    }

    private void validateSecondMethod(CourtRegistrationStatusRequestDTO registrationDTO) {
        CourtStatus courtStatus = registrationDTO.getStatus();

        if (courtStatus.getAlias().equals(REGISTERED_IN_COURT)
                && (registrationDTO.getRegNumber() == null || registrationDTO.getRegDate() == null))
            throw new CourtValidationException(REGISTRATION_FIELDS);

        if (courtStatus.getAlias().equals(CANCELLED)
                && (registrationDTO.getDeclinedDate() == null || registrationDTO.getDeclinedReasons() == null))
            throw new CourtValidationException(REGISTRATION_DECLINED_FIELDS);

        if (!registrationStatuses.contains(courtStatus.getAlias()))
            throw new CourtValidationException(STATUS_CODE_UNDEFINED);
    }

    private CourtCaseChancelleryDataRequest buildCourtCaseFields(CourtRegistrationStatusRequestDTO registration, AdmCase admCase) {
        Optional.ofNullable(registration.getDeclinedReasons())
                .ifPresent(courtDeclineReasonService::saveAnyNewReasonInReasons);

        return CourtCaseChancelleryDataRequest.builder()
                .caseId(registration.getCaseId())
                .claimId(registration.getClaimId())
                .registrationNumber(registration.getRegNumber())
                .registrationDate(registration.getRegDate())
                .declinedDate(registration.getDeclinedDate())
                .declinedReasons(registration.getDeclinedReasons())
                .status(registration.getStatus())
                .regionId(admCase.getCourtRegionId())
                .districtId(admCase.getCourtDistrictId())
                .build();
    }

}
