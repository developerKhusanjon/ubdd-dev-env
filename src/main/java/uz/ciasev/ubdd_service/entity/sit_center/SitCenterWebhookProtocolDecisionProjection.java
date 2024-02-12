package uz.ciasev.ubdd_service.entity.sit_center;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SitCenterWebhookProtocolDecisionProjection {

    Long getProtocolId();

    LocalDateTime getCreatedTime();
    LocalDateTime getUpdatedTime();

    Long getRegionId();

    Long getDistrictId();

    Long getMtpId();

    Long getOrganId();

    String getProtocolArticlePart();

    String getInspectorPinpp();

    Boolean getIsRealInspectorPinpp();

    Double getLatitude();

    Double getLongitude();

    String getSeries();

    String getNumber();

    Long getCaseStatus();

    String getCaseStatusName();

    String getLastName();

    String getFirstName();

    String getSecondName();

    String getDocumentSeries();

    String getDocumentNumber();

    String getPinpp();

    Boolean getIsRealPinpp();

    LocalDate getBirthDate();

    Long getDecisionStatus();

    String getDecisionStatusName();

    Long getDecisionTypeId();

    String getDecisionTypeName();

    LocalDate getExecutionDate();

    String getMainPunishmentType();

    String getMainPunishmentAmount();

    Long getResolutionOrganId();

    Long getAdmCaseOrganId();

    String getAdmCaseOrgan();

    String getResolutionOrgan();

    String getResolutionConsiderInfo();

    Long getProtocolArticlePartId();

    Long getVictimId();

    String getVictimPinpp();

    Long getMainPunishmentAmountSumm();

    Long getMainPunishmentPaidAmount();

    LocalDateTime getMainPunishmentLastPayTime();

    LocalDate getDiscountForDate70();

    Long getDiscountAmount70();

    LocalDate getDiscountForDate50();

    Long getDiscountAmount50();

    Long getDamageAmount();

    String getDamageTypeName();

    Long getDamageTypeId();

    Long getCompensationAmount();

    Long getCompensationPaidAmount();

    Long getTerminationReasonId();

    Long getPunishmentTypeId();


    default String getFormattedInspectorPinpp() {
        if (Boolean.TRUE.equals(this.getIsRealInspectorPinpp())) {
            return this.getInspectorPinpp();
        }
        return null;
    }

    default String getFormattedPinpp() {
        if (!this.getIsRealPinpp()) return null;

        return this.getPinpp();
    }

    default Long getStatus() {
        return Optional.ofNullable(getDecisionStatus()).orElseGet(this::getCaseStatus);
    }

    default String getStatusName() {
        return Optional.ofNullable(getDecisionStatusName()).orElseGet(this::getCaseStatusName);
    }
}
