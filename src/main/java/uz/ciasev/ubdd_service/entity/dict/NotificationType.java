package uz.ciasev.ubdd_service.entity.dict;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_notification_type")
@NoArgsConstructor
public class NotificationType extends AbstractBackendDict<NotificationTypeAlias> {

    public NotificationType(NotificationTypeAlias alias) {
        super(alias);
    }
}
