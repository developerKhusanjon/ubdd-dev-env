package uz.ciasev.ubdd_service.service.violation_event.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SentNotificationChecklist {

    private final Boolean mail;
    private final Boolean vai;
    private final Boolean mid;
    private final Boolean customs;
}
