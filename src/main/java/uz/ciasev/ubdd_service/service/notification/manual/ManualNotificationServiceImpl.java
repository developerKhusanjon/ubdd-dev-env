package uz.ciasev.ubdd_service.service.notification.manual;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.ManualNotificationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.notification.manual.ManualNotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManualNotificationServiceImpl implements ManualNotificationService {

    private final ManualNotificationRepository repository;

    @Override
    public ManualNotification create(User user, Decision decision, NotificationTypeAlias notificationType, ManualNotificationRequestDTO requestDTO) {
        ManualNotification notification = ManualNotification.builder()
                .user(user)
                .decision(decision)
                .notificationType(notificationType)
                .sendDate(requestDTO.getSentDate())
                .receiveDate(requestDTO.getReceiveDate())
                .number(requestDTO.getNumber())
                .text(requestDTO.getText())
                .fileUri(requestDTO.getFileUri())
                .build();

        return repository.save(notification);
    }

    @Override
    public ManualNotification getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ManualNotification.class, id));
    }

    @Override
    public Optional<ManualNotification> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ManualNotification> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<ManualNotification> findByDecision(Long decisionId) {
        return repository.findAllByDecisionId(decisionId);
    }
}
