package uz.ciasev.ubdd_service.service.notification.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.notification.SystemUserNotificationInfoResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.notification.SystemUserNotificationResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Collection;
import java.util.Map;

public interface SystemUserNotificationService {

    Page<SystemUserNotificationResponseDTO> findAllNotificationsByUser(User user, Map<String, String> filters, Pageable pageable);

    SystemUserNotificationInfoResponseDTO getNotificationsInfoByUser(User user);

    void markRead(User user, Collection<Long> notificationIds);

    void sendBroadcast(SystemUserNotificationBroadcastRequestDTO requestDTO);
}
