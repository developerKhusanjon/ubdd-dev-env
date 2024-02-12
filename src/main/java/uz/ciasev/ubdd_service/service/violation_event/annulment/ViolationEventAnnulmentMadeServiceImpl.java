package uz.ciasev.ubdd_service.service.violation_event.annulment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.service.ViolationEventApiService;
import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventAnnulmentRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;
import uz.ciasev.ubdd_service.service.violation_event.decision.ViolationEventResolvingValidationService;
import uz.ciasev.ubdd_service.service.violation_event.ViolationEventResultService;


@Service
@RequiredArgsConstructor
public class ViolationEventAnnulmentMadeServiceImpl implements ViolationEventAnnulmentMadeService {

    private final ViolationEventResolvingValidationService validationService;
    private final ViolationEventResultService resultService;
    private final ViolationEventAnnulmentService annulmentService;
    private final ViolationEventApiService eventService;


    @Override
    @Transactional
    public void annul(User user, ViolationEventAnnulmentRequestDTO requestDTO) {
        Long eventId = requestDTO.getViolationEventId();
        validationService.checkNotResolved(eventId);
        eventService.existById(eventId);

        ViolationEventAnnulment annulment = annulmentService.create(requestDTO);

        resultService.create(user, eventId, annulment);
    }
}
