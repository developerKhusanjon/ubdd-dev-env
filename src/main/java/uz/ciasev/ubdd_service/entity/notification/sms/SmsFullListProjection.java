package uz.ciasev.ubdd_service.entity.notification.sms;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SmsFullListProjection {

    Long getId();
    String getMessageId();
    String getMessage();
    LocalDateTime getSendTime();
    LocalDateTime getReceiveTime();
    String getPhoneNumber();
    String getNotificationType();
    Long getDeliveryStatusId();
    String getViolatorFirstNameLat();
    String getViolatorSecondNameLat();
    String getViolatorLastNameLat();
    LocalDate getViolatorBirthDate();
    String getAdmCaseNumber();
    Long getAdmCaseOrganId();
    Long getAdmCaseRegionId();
    Long getAdmCaseDistrictId();
    Long getAdmCaseId();
}
