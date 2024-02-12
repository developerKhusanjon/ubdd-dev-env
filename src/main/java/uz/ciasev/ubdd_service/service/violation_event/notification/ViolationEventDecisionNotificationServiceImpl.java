package uz.ciasev.ubdd_service.service.violation_event.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.service.notification.mail.MailNotificationService;
import uz.ciasev.ubdd_service.service.pdf.PdfService;
import uz.ciasev.ubdd_service.service.vehicle.sending.castoms.SendDecisionToCustomsService;
import uz.ciasev.ubdd_service.service.vehicle.sending.mid.SendDecisionToMidService;
import uz.ciasev.ubdd_service.service.vehicle.sending.vai.MoveDecisionToVaiService;

import static uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias.CUSTOMS_EVENT;

@Service
@RequiredArgsConstructor
public class ViolationEventDecisionNotificationServiceImpl implements ViolationEventDecisionNotificationService {
    private final MailNotificationService mailNotificationService;
    private final PdfService pdfService;
    private final SendDecisionToCustomsService sendToCustomsService;
    private final SendDecisionToMidService sendToMidService;
    private final MoveDecisionToVaiService sendToVaiService;

    @Override
    public SentNotificationChecklist accept(Decision decision, UbddTexPassData texPass) {
        return SentNotificationChecklist.builder()
                .customs(sendToCustomsIfNeed(decision, texPass))
                .mail(sendMailIfNeed(decision, texPass))
                .mid(sendToMidIfNeed(decision, texPass))
                .vai(moveToVaiIfNeed(decision, texPass))
                .build();
    }


    private boolean sendMailIfNeed(Decision decision, UbddTexPassData texPass) {
        if (texPass.getVehicleNumberType() == null || !texPass.getVehicleNumberType().getIsNeedSendToMail()) {
            return false;
        }

        if (decision.getDecisionTypeAlias().not(DecisionTypeAlias.PUNISHMENT)) {
            return false;
        }

        mailNotificationService.sendMail(null, decision, NotificationTypeAlias.RESOLUTION_CREATE, pdfService::getDecisionMailContent);
        return true;
    }

    private boolean sendToCustomsIfNeed(Decision decision, UbddTexPassData texPass) {
        if (texPass.getExternalSystem() == null || !CUSTOMS_EVENT.equals(texPass.getExternalSystem())) {
            return false;
        }

        if (decision.getDecisionTypeAlias().not(DecisionTypeAlias.PUNISHMENT)) {
            return false;
        }

        sendToCustomsService.accept(texPass.getExternalId(), decision);
        return true;
    }

    private boolean sendToMidIfNeed(Decision decision, UbddTexPassData texPass) {
        if (texPass.getVehicleNumberType() == null || !texPass.getVehicleNumberType().getIsNeedSendToMID()) {
            return false;
        }

        // уточнить
        if (decision.getDecisionTypeAlias().not(DecisionTypeAlias.PUNISHMENT)) {
            return false;
        }

        sendToMidService.accept(decision, texPass);
        return true;
    }

    private boolean moveToVaiIfNeed(Decision decision, UbddTexPassData texPass) {
        if (texPass.getVehicleNumberType() == null || !texPass.getVehicleNumberType().getIsNeedMoveToVAI()) {
            return false;
        }

        // уточнить
        if (decision.getDecisionTypeAlias().not(DecisionTypeAlias.PUNISHMENT)) {
            return false;
        }

        sendToVaiService.accept(decision, texPass);
        return true;
    }
}
