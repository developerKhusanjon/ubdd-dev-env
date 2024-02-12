package uz.ciasev.ubdd_service.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.dto.internal.response.notification.DecisionNotificationListDTO;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.notification.mail.MailNotificationService;
import uz.ciasev.ubdd_service.service.notification.manual.ManualNotificationService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;
import uz.ciasev.ubdd_service.service.pdf.PdfService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DecisionNotificationServiceImpl implements DecisionNotificationService {

    private final DecisionAccessService decisionAccessService;
    private final MailNotificationService mailNotificationService;
    private final SmsNotificationService smsNotificationService;
    private final ManualNotificationService manualNotificationService;
    private final FileService fileService;
    private final PdfService pdfService;

//    @Override
//    public Optional<? extends DecisionNotification> findReceivedMibNotificationByDecision(Decision decision) {
//
//        if (decisionAccessService.is315(decision)) {
//            Optional<Decision> originalDecision = decisionAccessService.findOriginalDecisionFor315(decision);
//            if (originalDecision.isPresent()) {
//                Optional<SmsNotification> sms315 = smsNotificationService.findDeliveredByEntityAndEvent(originalDecision.get(), NotificationTypeAlias.RESOLUTION_CREATE)
//                        .stream()
//                        .findFirst();
//                if (sms315.isPresent()) return sms315;
//            }
//        }
//
//        Optional<MailNotification> mail = mailNotificationService.findDeliveredByEntityAndEvent(decision, NotificationTypeAlias.MIB_PRE_SEND)
//                .stream()
//                .findFirst();
//        if (mail.isPresent()) {
//            return mail;
//        }
//
//        Optional<SmsNotification> sms = smsNotificationService.findDeliveredByEntityAndEvent(decision, NotificationTypeAlias.MIB_PRE_SEND)
//                .stream()
//                .findFirst();
//        return sms;
//    }

    @Override
    public List<DecisionNotification> findMibNotificationByDecision(Decision decision) {

        ArrayList<DecisionNotification> notifications = new ArrayList<>();

        if (decisionAccessService.is315(decision)) {
            Optional<Decision> originalDecision = decisionAccessService.findOriginalDecisionFor315(decision);
            if (originalDecision.isPresent()) {
                List<SmsNotification> sms315List = smsNotificationService.findByEntityAndEvent(originalDecision.get(), NotificationTypeAlias.RESOLUTION_CREATE);
                notifications.addAll(sms315List);
            }
        }

        List<MailNotification> mailList = mailNotificationService.findByEntityAndEvent(decision, NotificationTypeAlias.MIB_PRE_SEND);
        notifications.addAll(mailList);


        List<SmsNotification> smsList = smsNotificationService.findByEntityAndEvent(decision, NotificationTypeAlias.MIB_PRE_SEND);
        notifications.addAll(smsList);

        return notifications;
    }

    @Override
    public DecisionNotification getByChannelAndId(MibNotificationTypeAlias type, Long id) {
        switch (type) {
            case SMS: return smsNotificationService.getById(id);
            case MAIL:  return mailNotificationService.getById(id);
            case MANUAL: return manualNotificationService.getById(id);
            default: throw new LogicalException(String.format("Not implemented for notification type %s", type));
        }
    }

    @Override
    public List<? extends DecisionNotification> getAllByChannelAndIds(MibNotificationTypeAlias type, List<Long> ids) {
        switch (type) {
            case SMS: return smsNotificationService.getByIds(ids);
            case MAIL:  return mailNotificationService.getByIds(ids);
            case MANUAL: return manualNotificationService.getByIds(ids);
            default: throw new LogicalException(String.format("Not implemented for notification type %s", type));
        }
    }

    @Override
    public byte[] getFileContentByIdAndChannel(Long id, MibNotificationTypeAlias type) {
        switch (type) {
            case SMS: return pdfService.getSms(id).getContent();
            case MAIL:  return mailNotificationService.getContent(id);
            case MANUAL: return fileService.getOrThrow(manualNotificationService.getById(id).getFileUri());
            default: throw new LogicalException(String.format("Not implemented for notification type %s", type));
        }
    }

    @Override
    public List<DecisionNotificationListDTO> findAllNotificationByDecision(Long decisionId) {
        List<SmsNotification> smsList = smsNotificationService.findByDecision(decisionId);
        List<MailNotification> mailList = mailNotificationService.findByDecision(decisionId);
        List<ManualNotification> manualNotificationList = manualNotificationService.findByDecision(decisionId);

        return Stream.of(
                        smsList.stream().map(DecisionNotificationListDTO::new),
                        mailList.stream().map(DecisionNotificationListDTO::new),
                        manualNotificationList.stream().map(DecisionNotificationListDTO::new)
                )
                .flatMap(s -> s)
                .sorted((d1, d2) -> -1 * DateTimeUtils.compereNullable(d1.getSendDate(), d2.getSendDate()))
                .collect(Collectors.toList());
    }
}