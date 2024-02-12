package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SmsRequestDTO implements Serializable {

    private Organ organ;

    private AdmEntity entity;

    private NotificationTypeAlias notificationTypeAlias;

    private String mobile;

    private String message;

    public abstract Long getPersonId();

    public abstract Long getUserId();

    public abstract Long getViolatorId();

    public abstract Long getAdmCaseId();
}
