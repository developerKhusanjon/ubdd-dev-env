package uz.ciasev.ubdd_service.entity.notification.mail;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

@Data
public class MailNotificationRequest  {

    @Setter(AccessLevel.NONE)
    private Long userId;

    private Violator violator;

    private Decision decision;

    private NotificationTypeAlias notificationType;

    private String fio;

    private String address;

    private MailTransGeography geography;

//    private Long areaId;
//
//    // mail system region
//    private Long regionId;

    private OrganInfo organInfo;

    // our organ
    private Organ organ;

    private String messageNumber;

    private byte[] base64Content;

    public void setUser(User user) {
        if (user != null) this.userId = user.getId();
    }

    public Long getAreaId() {
        return geography.getExternalDistrictId();
    }

    public Long getRegionId() {
        return geography.getExternalRegionId();
    }
}

