package uz.ciasev.ubdd_service.dto.internal.response.notification;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MailNotificationListDTO {

    private final Long id;
    private final Long decisionId;
    private final Long decisionStatusId;
    private final String decisionNumber;
    private final String decisionSeries;
    private final LocalDate resolutionTime;
    private final Long resolutionOrganId;
    private final Long resolutionRegionId;
    private final Long resolutionDistrictId;
    private final LocalDate violatorBirtDate;
    private final String violatorFirstNameLat;
    private final String violatorSecondNameLat;
    private final String violatorLastNameLat;
    private final String address;
    private final Long decisionArticlePartId;
    private final Long decisionArticleViolationTypeId;
    private final String mainPunishmentAmountText;
    private final Long mainPunishmentTypeId;
    private final String additionalPunishmentAmountText;
    private final Long additionalPunishmentTypeId;
    private final String type;
    private final Long deliveryStatusId;
    private final String messageNumber;
    private final LocalDateTime sendDate;
    private final LocalDate performDate;
    private final LocalDateTime changeStatusTime;
    private final ApiUrl contentPdfUrl;

    public MailNotificationListDTO(MailNotificationListProjection mailNotificationProjection) {
        this.id = mailNotificationProjection.getId();
        this.decisionId = mailNotificationProjection.getDecisionId();
        this.decisionStatusId = mailNotificationProjection.getDecisionStatusId();
        this.decisionNumber = mailNotificationProjection.getDecisionNumber();
        this.decisionSeries = mailNotificationProjection.getDecisionSeries();
        this.resolutionTime = mailNotificationProjection.getResolutionTime();
        this.resolutionOrganId = mailNotificationProjection.getResolutionOrganId();
        this.resolutionRegionId = mailNotificationProjection.getResolutionRegionId();
        this.resolutionDistrictId = mailNotificationProjection.getResolutionDistrictId();
        this.violatorBirtDate = mailNotificationProjection.getViolatorBirtDate();
        this.violatorFirstNameLat = mailNotificationProjection.getViolatorFirstNameLat();
        this.violatorSecondNameLat = mailNotificationProjection.getViolatorSecondNameLat();
        this.violatorLastNameLat = mailNotificationProjection.getViolatorLastNameLat();
        this.address = mailNotificationProjection.getAddress();
        this.decisionArticlePartId = mailNotificationProjection.getDecisionArticlePartId();
        this.decisionArticleViolationTypeId = mailNotificationProjection.getDecisionArticleViolationTypeId();
        this.mainPunishmentAmountText = mailNotificationProjection.getMainPunishmentAmountText();
        this.mainPunishmentTypeId = mailNotificationProjection.getMainPunishmentTypeId();
        this.additionalPunishmentAmountText = mailNotificationProjection.getAdditionalPunishmentAmountText();
        this.additionalPunishmentTypeId = mailNotificationProjection.getAdditionalPunishmentTypeId();
        this.type = mailNotificationProjection.getNotificationTypeAlias().name();
        this.deliveryStatusId = mailNotificationProjection.getDeliveryStatusId();
        this.messageNumber = mailNotificationProjection.getMessageNumber();
        this.sendDate = mailNotificationProjection.getSendTime();
        this.performDate = mailNotificationProjection.getPerformDate();
        this.changeStatusTime = mailNotificationProjection.getChangeStatusTime();
        this.contentPdfUrl = ApiUrl.getSentMailContent(mailNotificationProjection);
    }
}
