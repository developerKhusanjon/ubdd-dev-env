package uz.ciasev.ubdd_service.service.notification.mail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface MailNotificationService {

    MailNotification sendMail(@Nullable User user, Decision decision, NotificationTypeAlias notificationTypeAlias, MailNotificationServiceImpl.MileContentBuilder contentBuilder);

    Page<MailNotificationListProjection> findByFilters(User user, Map<String, String> filtersValue, Pageable pageable);

    List<MailNotification> findByDecision(Long decisionId);

    List<MailNotification> findByEntityAndEvent(AdmEntity entity, NotificationTypeAlias notificationTypeAlias);

    MailNotification getById(Long id);

    byte[] getContent(Long id);

    List<MailNotification> getByIds(List<Long> ids);
}
