package uz.ciasev.ubdd_service.entity.protocol;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface ProtocolSimpleListProjection {

    Long getId();

    LocalDateTime getCreatedTime();

    LocalDateTime getEditedTime();

    String getSeries();

    String getNumber();

    String getOldSeries();

    String getOldNumber();

    LocalDateTime getRegistrationTime();

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

    Long getViolatorDocumentTypeId();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    Long getViolatorId();

    String getViolatorPinpp();

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    LocalDate getViolatorBirthDate();

    Long getAdmCaseId();

}
