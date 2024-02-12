package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.utils.FioUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProtocolFullListProjection {

    Long getId();

    LocalDateTime getCreatedTime();

    LocalDateTime getEditedTime();

    Long getRegistrationUserId();

    String getSeries();

    String getNumber();

    String getOldSeries();

    String getOldNumber();

    LocalDateTime getRegistrationTime();

    String getInspectorFio();

    String getInspectorWorkCertificate();

    String getInspectorInfo();

    String getInspectorSignature();

    Long getRegistrationOrganId();

    Long getRegistrationDepartmentId();

    Long getRegistrationRegionId();

    Long getRegistrationDistrictId();

    Long getMtpId();

    LocalDateTime getViolationTime();

    Long getArticleId();

    Long getArticlePartId();

    Long getArticleViolationTypeId();

    Long getViolatorDetailId();

    Boolean getIsJuridic();

    Long getJuridicId();

    boolean getIsAgree();

    boolean getIsFamiliarize();

    boolean getIsMain();

    boolean getIsDeleted();

    boolean getIsArchived();

    Double getLatitude();

    Double getLongitude();

    String getAudioUri();

    String getVideoUri();

    Long getInspectorRegionId();

    Long getInspectorDistrictId();

    Long getInspectorPositionId();

    Long getInspectorRankId();

    Long getViolatorDocumentTypeId();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    Long getViolatorId();

    String getViolatorPinpp();

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    LocalDate getViolatorBirthDate();

    Long getViolatorNationalityId();

    Long getViolatorGenderId();

    Long getAdmCaseId();

    Long getAdmCaseStatusId();

    Long getConsiderOrganId();

    Long getConsiderDepartmentId();

    Long getConsiderRegionId();

    Long getConsiderDistrictId();

    Long getConsiderUserId();

    Long getDecisionId();

    String getDecisionSeries();

    String getDecisionNumber();

    LocalDateTime getResolutionTime();

    Long getResolutionOrganId();

    Long getDecisionTypeId();

    Long getTerminationReasonId();

    Long getPunishmentTypeId();

    String getPunishmentAmount();

    Long getDecisionStatusId();

    default String getViolatorFio() {
        return FioUtils.buildFullFio(
                this.getViolatorFirstNameLat(),
                this.getViolatorSecondNameLat(),
                this.getViolatorLastNameLat());
    }

    String getVehicleNumber();

    String getVehicleBrand();

    Long getGovCompensationAmount();

    Long getGovCompensationPaidAmount();
}
