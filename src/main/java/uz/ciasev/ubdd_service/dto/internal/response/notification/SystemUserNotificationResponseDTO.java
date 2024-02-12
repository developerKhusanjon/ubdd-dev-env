package uz.ciasev.ubdd_service.dto.internal.response.notification;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.notification.SystemUserNotification;

import java.time.LocalDateTime;

@Getter
public class SystemUserNotificationResponseDTO {

    private final Long id;

    private final LocalDateTime createdTime;

    private final String eventType;

    private final Long admCaseId;

    private final Long decisionId;

    private final String text;

    @JsonAlias("isRead")
    private final boolean isRead;

    private final LocalDateTime readTime;

    public SystemUserNotificationResponseDTO(SystemUserNotification notification) {
        this.id = notification.getId();
        this.createdTime = notification.getCreatedTime();
        this.eventType = notification.getNotificationType().name();
        this.admCaseId = notification.getAdmCaseId();
        this.decisionId = notification.getDecisionId();
        this.text = notification.getText();
        this.isRead = notification.isRead();
        this.readTime = notification.getReadTime();
    }
}
