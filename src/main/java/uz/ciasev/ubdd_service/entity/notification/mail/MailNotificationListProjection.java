package uz.ciasev.ubdd_service.entity.notification.mail;

import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MailNotificationListProjection {

    Long getId();

    Long getDecisionId();

    Long getDecisionStatusId();

    String getDecisionNumber();

    String getDecisionSeries();

    LocalDate getResolutionTime();

    Long getResolutionOrganId();

    Long getResolutionRegionId();

    Long getResolutionDistrictId();

    LocalDate getViolatorBirtDate();

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    String getAddress();

    Long getDecisionArticlePartId();

    Long getDecisionArticleViolationTypeId();

    String getMainPunishmentAmountText();

    Long getMainPunishmentTypeId();

    String getAdditionalPunishmentAmountText();

    Long getAdditionalPunishmentTypeId();

    NotificationTypeAlias getNotificationTypeAlias();

    Long getDeliveryStatusId();

    LocalDateTime getChangeStatusTime();

    String getMessageNumber();

    LocalDateTime getSendTime();

    LocalDate getPerformDate();
}
