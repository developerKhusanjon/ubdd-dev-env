package uz.ciasev.ubdd_service.service.violation_event.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.service.ViolationEventApiService;
import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.ubdd_data.MultiSystemTechPassSupplier;
import uz.ciasev.ubdd_service.service.violation_event.ViolationEventResultService;
import uz.ciasev.ubdd_service.service.violation_event.notification.ViolationEventDecisionNotificationService;


@Service
@RequiredArgsConstructor
public class ViolationEventDecisionMadeServiceImpl implements ViolationEventDecisionMadeService {
    private final ViolationEventResolvingValidationService validationService;
    private final ViolationEventApiService eventService;
    private final MultiSystemTechPassSupplier techPassSupplier;
    private final AdmCaseService admCaseService;
    private final ResolutionForViolationEventCreateService resolutionService;
    private final ViolationEventResultService resultService;
    private final CreateProtocolFromViolationEvent protocolService;
    private final ViolationEventPhotoSaveService photoSaveService;
    private final ViolationEventDecisionNotificationService notificationService;


    @Override
    @Transactional
    public Decision resolve(User user, ViolationEventDecisionRequestDTO requestDTO) {
        Long eventId = requestDTO.getViolationEventId();
        validationService.checkNotResolved(eventId);

        ViolationEventApiDTO event = eventService.getById(eventId);
        validationService.checkCompletenessOfDataToMakeDecision(event);

        UbddTexPassDTOI texPassData = techPassSupplier.getByExternalId(requestDTO);
        validationService.checkCompletenessOfDataToMakeDecision(texPassData);

        AdmCase admCase = admCaseService.createEmptyAdmCase(user);
        photoSaveService.save(user, event, admCase);
        Pair<Protocol, UbddTexPassData> protocolWithTexPass = protocolService.create(user, event, admCase, texPassData);
        Protocol protocol = protocolWithTexPass.getFirst();
        UbddTexPassData savedTexPass = protocolWithTexPass.getSecond();
        Decision decision = resolutionService.create(user, event, admCase, protocol).getCreatedDecision().getDecision();

        resultService.create(user, eventId, decision);

        notificationService.accept(decision, savedTexPass);

        return decision;
    }

}