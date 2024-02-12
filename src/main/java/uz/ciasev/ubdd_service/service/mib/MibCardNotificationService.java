package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.notification.ManualNotificationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.service.notification.DecisionNotificationService;
import uz.ciasev.ubdd_service.service.notification.manual.ManualNotificationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MibCardNotificationService {

    private final MibExecutionCardRepository cardRepository;
    private final DecisionNotificationService notificationService;
    private final AliasedDictionaryService<DocumentType, DocumentTypeAlias> documentTypeService;
    private final ManualNotificationService manualNotificationService;


    public Optional<DecisionNotification> getPresetNotification(MibExecutionCard card) {
        if (card.getNotificationId() == null) {
            return Optional.empty();
        }

        return Optional.of(notificationService.getByChannelAndId(card.getNotificationTypeAlias(), card.getNotificationId()));
    }


    public Optional<Pair<byte[], DocumentType>> getPresetNotificationFile(MibExecutionCard card) {
        if (card.getNotificationId() == null) {
            return Optional.empty();
        }

        byte[] content = notificationService.getFileContentByIdAndChannel(card.getNotificationId(), card.getNotificationTypeAlias());

        DocumentTypeAlias documentTypeAlias = card.getNotificationTypeAlias() == MibNotificationTypeAlias.MANUAL
                ? DocumentTypeAlias.MIB_NOTIFICATION_MANUAL_MAIL
                : DocumentTypeAlias.MIB_NOTIFICATION_MAIL;
        DocumentType documentType = documentTypeService.getByAlias(documentTypeAlias);

        return Optional.of(Pair.of(content, documentType));
    }

    @Transactional
    public void setAutoNotification(MibExecutionCard card) {

        if (card.getNotificationTypeAlias() != null) {
            if (MibNotificationTypeAlias.MANUAL == card.getNotificationTypeAlias()) {
                return;
            }

            if (getPresetNotification(card).get().getReceiveDate() != null) {
                return;
            }
        }

        Optional<DecisionNotification> bestNotification = findBestNotificationForCard(card);
        if (bestNotification.isEmpty()) return;

        setNotificationToCard(card, bestNotification.get());
    }

    @Transactional
    public MibExecutionCard setManualNotification(User user,
                                                  MibExecutionCard card,
                                                  ManualNotificationRequestDTO requestDTO) {
        ManualNotification manualNotification = manualNotificationService.create(user,
                card.getDecision(),
                NotificationTypeAlias.MIB_PRE_SEND,
                requestDTO);

        return setNotificationToCard(card, manualNotification);
    }

    private MibExecutionCard setNotificationToCard(MibExecutionCard card, DecisionNotification notification) {
        card.setNotificationTypeAlias(notification.getChannel());
        card.setNotificationId(notification.getId());
        return cardRepository.save(card);
    }

    private Optional<DecisionNotification> findBestNotificationForCard(MibExecutionCard card) {

        List<DecisionNotification> notifications = notificationService.findMibNotificationByDecision(card.getDecision());

        if (notifications.isEmpty()) return Optional.empty();

        Optional<DecisionNotification> receivedNotification = notifications.stream()
                .filter(n -> n.getReceiveDate() != null)
                .findFirst();
        if (receivedNotification.isPresent()) {
            return receivedNotification;
        }

        Optional<DecisionNotification> sendNotification = notifications.stream()
                .filter(n -> n.getSendDate() != null)
                .findFirst();
        if (sendNotification.isPresent()) {
            return sendNotification;
        }

        return notifications.stream()
                .findFirst();
    }
}