package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;
import uz.ciasev.ubdd_service.utils.FormatUtils;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class AdmCaseDeletionRequestResponseDTO {

    private final Long id;
    private final Long admCaseId;
    private final Long admCaseStatusId;
    private final LocalDateTime admCaseCreatedTime;
    private final Long admCaseOrganId;
    private final Long admCaseRegionId;
    private final Long admCaseDistrictId;
    private final Long statusId;
    private final LocalDateTime createdTime;
    private final Long applicantId;
    private final String applicantFirstName;
    private final String applicantLastName;
    private final String applicantSecondName;
    private final String applicantMobile;
    private final Long applicantRankId;
    private final String applicantUsername;
    private final Long deleteReasonId;
    private final LocalFileUrl documentBaseUrl;
    private final LocalDateTime editedTime;
    private final Long registrationId;
    private final Long adminId;
    private final String adminFirstName;
    private final String adminLastName;
    private final String adminSecondName;
    private final Long adminRankId;
    private final String adminUsername;
    private final Long declineReasonId;
    private final String declineComment;
    private final LocalDateTime recoveredTime;
    private final Long recoveredUserId;
    private final String recoveredUserFirstName;
    private final String recoveredUserLastName;
    private final String recoveredUserSecondName;
    private final Long recoveredUserRankId;
    private final String recoveredUserUsername;

    public AdmCaseDeletionRequestResponseDTO(AdmCaseDeletionRequestProjection p) {
        this.id = p.getId();
        this.admCaseId = p.getAdmCaseId();
        this.admCaseStatusId = p.getAdmCaseStatusId();
        this.admCaseCreatedTime = p.getAdmCaseCreatedTime();
        this.admCaseOrganId = p.getAdmCaseOrganId();
        this.admCaseRegionId = p.getAdmCaseRegionId();
        this.admCaseDistrictId = p.getAdmCaseDistrictId();
        this.statusId = p.getStatusId();
        this.createdTime = p.getCreatedTime();
        this.applicantId = p.getApplicantId();
        this.applicantFirstName = p.getApplicantFirstName();
        this.applicantLastName = p.getApplicantLastName();
        this.applicantSecondName = p.getApplicantSecondName();
        this.applicantMobile = FormatUtils.mobileToText(p.getApplicantMobile());
        this.applicantRankId = p.getApplicantRankId();
        this.applicantUsername = p.getApplicantUsername();
        this.deleteReasonId = p.getDeleteReasonId();
        this.documentBaseUrl = LocalFileUrl.ofNullable(p.getDocumentBaseUri());
        this.editedTime = p.getEditedTime();
        this.registrationId = p.getRegistrationId();
        this.adminId = p.getAdminId();
        this.adminFirstName = p.getAdminFirstName();
        this.adminLastName = p.getAdminLastName();
        this.adminSecondName = p.getAdminSecondName();
        this.adminRankId = p.getAdminRankId();
        this.adminUsername = p.getAdminUsername();
        this.declineReasonId = p.getDeclineReasonId();
        this.declineComment = p.getDeclineComment();
        this.recoveredTime = p.getRecoveredTime();
        this.recoveredUserId = p.getRecoveredUserId();
        this.recoveredUserFirstName = p.getRecoveredUserFirstName();
        this.recoveredUserLastName = p.getRecoveredUserLastName();
        this.recoveredUserSecondName = p.getRecoveredUserSecondName();
        this.recoveredUserRankId = p.getRecoveredUserRankId();
        this.recoveredUserUsername = p.getRecoveredUserUsername();
    }
}
