package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class SystemUserNotificationBroadcastRequestDTO implements Serializable {

    private Organ organ;

    private Department department;

    private Region region;

    private District district;

    private Long admCaseId;

    private Long decisionId;

    private NotificationTypeAlias type;

    private String text;

    public SystemUserNotificationBroadcastRequestDTO(AdmCase admCase, NotificationTypeAlias notificationTypeAlias, String text) {
        this.type = notificationTypeAlias;
        this.text = text;
        this.admCaseId = admCase.getId();
        this.decisionId = null;
        this.organ = admCase.getOrgan();
        this.department = admCase.getDepartment();
        this.region = admCase.getRegion();
        this.district = admCase.getDistrict();

    }

    public SystemUserNotificationBroadcastRequestDTO(AdmCase admCase, Decision decision, NotificationTypeAlias notificationTypeAlias, String text) {
        this(admCase, notificationTypeAlias, text);
        this.decisionId = decision.getId();
    }
}
