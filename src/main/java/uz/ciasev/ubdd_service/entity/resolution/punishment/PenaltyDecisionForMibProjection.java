package uz.ciasev.ubdd_service.entity.resolution.punishment;

import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PenaltyDecisionForMibProjection {

    Long getId();

    String getSeries();

    String getNumber();

    LocalDate getExecutionFromDate();

    LocalDateTime getResolutionTime();

    Long getPenaltyAmount();

    Long getPenaltyPaidAmount();

    LocalDateTime getPenaltyLastPayTime();

    Long getViolatorId();

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    String getViolatorAddressText();

    Long getMibRegionId();

    Long getMibDistrictId();

    MibNotificationTypeAlias getPresetNotificationTypeAlias();

    Long getPresetNotificationId();

    String getMibSendMessage();

    String getMibCaseNumber();

    Long getMibCaseStatusId();

    Long getMibCaseStatusTypeId();

    Long getTotalRecoveredAmount();

    LocalDateTime getSendTime();

    LocalDateTime getAcceptTime();

    LocalDateTime getReturnTime();

    LocalDate getSmsSendDate();

    LocalDate getSmsReceiveDate();

    Long getSmsDeliveryStatusId();

    LocalDate getMailSendDate();

    LocalDate getMailReceiveDate();

    Long getMailDeliveryStatusId();

}
