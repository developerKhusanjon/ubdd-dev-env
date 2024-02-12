package uz.ciasev.ubdd_service.entity.dict.mib;

import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_mib_notification_type")
public class MibNotificationType extends AbstractBackendDict<MibNotificationTypeAlias> {
}
