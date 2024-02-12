package uz.ciasev.ubdd_service.service.notification.manual;

import uz.ciasev.ubdd_service.dto.internal.request.notification.ManualNotificationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ManualNotificationService {

    ManualNotification create(User user, Decision decision, NotificationTypeAlias notificationType, ManualNotificationRequestDTO requestDTO);

    ManualNotification getById(Long id);

    Optional<ManualNotification> findById(Long id);

    List<ManualNotification> getByIds(List<Long> ids);

    List<ManualNotification> findByDecision(Long decisionId);
}
