package uz.ciasev.ubdd_service.entity.admcase;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdmCaseListProjection {

    Long getId();

    LocalDateTime getCreatedTime();

    LocalDateTime getEditedTime();

    Long getUserId();

    Long getStatusId();

    String getSeries();

    String getNumber();

    LocalDate getOpenedDate();

    LocalDateTime getConsideredTime();

    String getConsiderInfo();

    boolean isDeleted();

    Long getConsiderUserId();

    Long getOrganId();

    Long getDepartmentId();

    Long getRegionId();

    Long getDistrictId();

    Long getMergedToAdmCaseId();

    Long getClaimId();

    Long getCourtRegionId();

    Long getCourtDistrictId();

    String getCourtOutNumber();

    LocalDate getCourtOutDate();

    Long getCourtConsideringBasisId();

    Long getCourtConsideringAdditionId();

    // CourtCaseFields
    LocalDateTime getCourtHearingDate();

    Long getCourtStatusId();

    String getJudge();

    // Calculated
    String getViolators();

    String getArticleParts();

    Long getProtocolCount();
}
