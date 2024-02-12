package uz.ciasev.ubdd_service.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.mib.MibValidationService;
import uz.ciasev.ubdd_service.service.notification.mail.MailNotificationService;
import uz.ciasev.ubdd_service.service.pdf.PdfService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import java.time.LocalDate;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.SEND_DECISION_TO_MAIL;
import static uz.ciasev.ubdd_service.entity.action.ActionAlias.SEND_MIB_PRESENT_TO_MAIL;

@Service
@RequiredArgsConstructor
public class DecisionMailNotificationServiceImpl implements DecisionMailNotificationService {

    private final DecisionAccessService decisionAccessService;
    private final MailNotificationService mailNotificationService;
    private final DecisionService decisionService;
    private final PdfService pdfService;

    @Override
    public void sendDecisionToMail(User user, Long decisionId) {

        Decision decision = decisionService.getById(decisionId);

        decisionAccessService.checkIsNotCourt(decision);
        decisionAccessService.checkUserActionPermit(user, SEND_DECISION_TO_MAIL, decision);

        mailNotificationService.sendMail(user, decision, NotificationTypeAlias.RESOLUTION_CREATE, pdfService::getDecisionMailContent);
    }

    @Override
    public void sendMibPresentToMail(User user, Long decisionId) {
        Decision decision = decisionService.getById(decisionId);

        decisionAccessService.checkIsNotCourt(decision);
        decisionAccessService.checkUserActionPermit(user, SEND_MIB_PRESENT_TO_MAIL, decision);

        checkMibPresentNotEarly(decision);

        mailNotificationService.sendMail(user, decision, NotificationTypeAlias.MIB_PRE_SEND, pdfService::getMibPresentMailContent);
    }

    private void checkMibPresentNotEarly(Decision decision) {
        LocalDate executionFromDate = decision.getExecutionFromDate();

        if (executionFromDate.isAfter(MibValidationService.maxExecutionDateForNotification())) {
            // TODO: 09.11.2023 validation temporarily disabled
//            throw new EarlyMibSendException();
        }
        if (executionFromDate.isBefore(MibValidationService.minExecutionDateForNotification())) {
            //         2022-05-10 Бегзод сказал вообше убрать проверку на слишком позднюю отправку в миб
//            throw new LateMibSendException();
        }
    }
}