package uz.ciasev.ubdd_service.entity.webhook.replica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ReplicaWebhookProtocolDecisionProjection {

    Long getProtocolId();

    Long getOrganId();

    Long getMtpId();

    String getInspectorPinpp();

    LocalDateTime getCreatedTime();

    Long getRegionId();
    Long getDistrictId();

    String getProtocolArticlePart();

    Boolean getIsRealInspectorPinpp();

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


    String getMainPunishmentType();

    String getMainPunishmentAmount();

    Long getResolutionOrganId();

    Long getAdmCaseOrganId();

    String getAdmCaseOrgan();

    String getResolutionOrgan();

    String getResolutionConsiderInfo();

    String getRegionValue();
    String getDistrictValue();

    String getOrganValue();
    String getMtpValue();
    LocalDate getSendDateTime();


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
