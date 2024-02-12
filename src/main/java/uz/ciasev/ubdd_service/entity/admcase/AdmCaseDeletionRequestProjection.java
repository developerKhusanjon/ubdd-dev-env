package uz.ciasev.ubdd_service.entity.admcase;

import java.time.LocalDateTime;

public interface AdmCaseDeletionRequestProjection {

    Long getId();

    Long getAdmCaseId();

    Long getAdmCaseStatusId();

    LocalDateTime getAdmCaseCreatedTime();

    Long getAdmCaseOrganId();

    Long getAdmCaseRegionId();

    Long getAdmCaseDistrictId();

    Long getStatusId();

    LocalDateTime getCreatedTime();

    Long getApplicantId();

    String getApplicantFirstName();

    String getApplicantLastName();

    String getApplicantSecondName();

    String getApplicantMobile();

    Long getApplicantRankId();

    String getApplicantUsername();

    Long getDeleteReasonId();

    String getDocumentBaseUri();

    LocalDateTime getEditedTime();

    Long getRegistrationId();

    Long getAdminId();

    String getAdminFirstName();

    String getAdminLastName();

    String getAdminSecondName();

    Long getAdminRankId();

    String getAdminUsername();

    Long getDeclineReasonId();

    String getDeclineComment();

    LocalDateTime getRecoveredTime();

    Long getRecoveredUserId();

    String getRecoveredUserFirstName();

    String getRecoveredUserLastName();

    String getRecoveredUserSecondName();

    Long getRecoveredUserRankId();

    String getRecoveredUserUsername();
}
