package uz.ciasev.ubdd_service.entity.protocol;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProtocolRequirementProjection {

    Long getProtocolId();
    String getProtocolNumber();
    String getProtocolSeries();
    String getOrganName();
    Long getOrganId();
    String getDistrictName();
    Long getProtocolDistrictId();
    String getRegionName();
    Long getProtocolRegionId();
    LocalDateTime getRegistrationTime();
    Long getViolatorId();
    String getViolatorFirstNameLat();
    String getViolatorSecondNameLat();
    String getViolatorLastNameLat();
    String getViolatorPinpp();
    LocalDate getViolatorBirthDate();
    String getDecisionStatus();
    Long getResolutionId();
    Long getAdmCaseId();
    String getAdmCaseStatus();
    Long getAdmCaseStatusId();
    LocalDateTime getResolutionTime();
    LocalDateTime getAdmCaseConsideredTime();
    Boolean getDecisionIsArticle34();
    String getDecisionNumber();
    String getDecisionSeries();
    String getMainPunishmentType();
    String getMainPunishmentAmount();
    String getAdditionalPunishmentType();
    String getAdditionalPunishmentAmount();
    Long getCompensationAmount();
    Long getCompensationPaidAmount();
}
