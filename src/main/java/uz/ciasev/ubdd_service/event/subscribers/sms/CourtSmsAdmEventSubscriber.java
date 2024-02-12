package uz.ciasev.ubdd_service.event.subscribers.sms;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.event.subscribers.AdmCaseRegistrationStatusInCourtSubscriber;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationDTOService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;
import uz.ciasev.ubdd_service.service.utils.AdmCaseOrganService;

import java.util.List;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.CANCELLED;

//@Service
@RequiredArgsConstructor
public class CourtSmsAdmEventSubscriber extends AdmCaseRegistrationStatusInCourtSubscriber {

    private final SmsNotificationService smsNotificationService;
    private final SmsNotificationDTOService smsNotificationDTOService;
    private final AdmCaseOrganService entityOrganService;


    @Override
    public void apply(CourtCaseChancelleryData courtCaseFields) {
        if (courtCaseFields.getStatus().is(CANCELLED)) {
            return;
        }

        Organ organ = entityOrganService.byAdmCaseId(courtCaseFields.getCaseId());
        if (!organ.getSmsNotification()) {
            return;
        }
        List<SmsRequestDTO> notifications = smsNotificationDTOService.makeCourtDTO(courtCaseFields, organ);
        notifications.forEach(smsNotificationService::sendSms);
    }
}
