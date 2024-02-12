package uz.ciasev.ubdd_service.entity.ombudsman;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OmbudsmanWebhookProtocolDecisionProjection {

    Long getProtocolSeries();

    Long getRegionId();

    Long getDistrictId();

    LocalDateTime getCreatedTime();

    Long getOrganId();

    Long getCaseStatus();

    Long getDecisionStatus();

    String getCaseStatusName();

    String getDecisionStatusName();

    Long getMtpId();

    String getProtocolArticlePart();

    String getInspectorPinpp();

    Boolean getIsRealInspectorPinpp();

    String getSeries();

    String getNumber();

    String getLastName();

    String getFirstName();

    String getSecondName();

    LocalDate getBirthDate();

    String getEmploymentPlace();

    String getEmploymentPosition();

    String getInspectorFio();

    String getMainPunishmentAmount();
    String getMainPunishmentType();

    String getTerminationReasonId();

    Long getResolutionOrganId();

    String getAdmCaseOrgan();

    String getOrganValue();

    String getRegionValue();
    String getDistrictValue();
    String getResolutionOrgan();
    Long getFromOrganId();
    Long getToOrganId();
    String getResolutionConsiderInfo();

    default String getFormattedInspectorPinpp() {
        if (Boolean.TRUE.equals(this.getIsRealInspectorPinpp())) {
            return this.getInspectorPinpp();
        }
        return null;
    }

    default Long getStatus() {
        return Optional.ofNullable(getDecisionStatus()).orElseGet(this::getCaseStatus);
    }

    default String getStatusName() {
        return Optional.ofNullable(getDecisionStatusName()).orElseGet(this::getCaseStatusName);
    }

}
