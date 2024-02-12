package uz.ciasev.ubdd_service.service.search.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyDecisionForMibProjection;
import uz.ciasev.ubdd_service.service.mib.MibValidationService;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Getter
public class PenaltyForSendToMibResponseDTO {

    private final Long decisionId;
    private final String decisionSeries;
    private final String decisionNumber;
    private final LocalDate executionFromDate;
    private final LocalDateTime resolutionTime;
    private final Long penaltyAmount;
    private final Long penaltyPaidAmount;
    private final LocalDateTime penaltyLastPayTime;
    private final Long violatorId;
    private final String violatorFirstNameLat;
    private final String violatorSecondNameLat;
    private final String violatorLastNameLat;
    private final String violatorAddressText;
    private final Long mibRegionId;
    private final Long mibDistrictId;
    private final LocalDate mibNotificationReceiveDate;
    private final Long mibNotificationTypeId;
    private final String mibSendMessage;
    private final String mibCaseNumber;
    private final Long mibCaseStatusId;
    private final Long mibCaseStatusTypeId;
    private final Long totalRecoveredAmount;
    private final LocalDateTime sendTime;
    private final LocalDateTime acceptTime;
    private final LocalDateTime returnTime;

    private final LocalDate smsSendDate;
    private final LocalDate smsReceiveDate;
    private final Long smsDeliveryStatusId;
    private final LocalDate mailSendDate;
    private final LocalDate mailReceiveDate;
    private final Long mailDeliveryStatusId;

    private final LocalDate sendExpiredDate;
    private final Boolean hasNotification;
    private final Long amountOfRecovery;



    public PenaltyForSendToMibResponseDTO(PenaltyDecisionForMibProjection projection, @Nullable DecisionNotification presetNotification) {
        this.decisionId = projection.getId();
        this.decisionSeries = projection.getSeries();
        this.decisionNumber = projection.getNumber();
        this.executionFromDate = projection.getExecutionFromDate();
        this.resolutionTime = projection.getResolutionTime();
        this.penaltyAmount = projection.getPenaltyAmount();
        this.penaltyPaidAmount = projection.getPenaltyPaidAmount();
        this.penaltyLastPayTime = projection.getPenaltyLastPayTime();
        this.violatorId = projection.getViolatorId();
        this.violatorFirstNameLat = projection.getViolatorFirstNameLat();
        this.violatorSecondNameLat = projection.getViolatorSecondNameLat();
        this.violatorLastNameLat = projection.getViolatorLastNameLat();
        this.violatorAddressText = projection.getViolatorAddressText();
        this.mibRegionId = projection.getMibRegionId();
        this.mibDistrictId = projection.getMibDistrictId();
        this.mibNotificationReceiveDate = Optional.ofNullable(presetNotification).map(DecisionNotification::getReceiveDate).orElse(null);
        this.mibNotificationTypeId = Optional.ofNullable(projection.getPresetNotificationTypeAlias()).map(MibNotificationTypeAlias::getId).orElse(null);
        this.mibSendMessage = projection.getMibSendMessage();
        this.mibCaseNumber = projection.getMibCaseNumber();
        this.mibCaseStatusId = projection.getMibCaseStatusId();
        this.mibCaseStatusTypeId = projection.getMibCaseStatusTypeId();
        this.totalRecoveredAmount = projection.getTotalRecoveredAmount();
        this.sendTime = projection.getSendTime();
        this.acceptTime = projection.getAcceptTime();
        this.returnTime = projection.getReturnTime();

        this.smsSendDate = projection.getSmsSendDate();
        this.smsReceiveDate = projection.getSmsReceiveDate();
        this.smsDeliveryStatusId = projection.getSmsDeliveryStatusId();
        this.mailSendDate = projection.getMailSendDate();
        this.mailReceiveDate = projection.getMailReceiveDate();
        this.mailDeliveryStatusId = projection.getMailDeliveryStatusId();

        this.sendExpiredDate = resolutionTime.toLocalDate().plusDays(MibValidationService.getMibSendMaxDays());
        this.hasNotification = Objects.nonNull(projection.getPresetNotificationId());
        this.amountOfRecovery = projection.getPenaltyAmount() - projection.getPenaltyPaidAmount();
    }
}
