package uz.ciasev.ubdd_service.entity.protocol;

import java.time.LocalDateTime;


public interface ProtocolSlimProjection {

    Long getId();

    LocalDateTime getCreatedTime();

    LocalDateTime getEditedTime();

    Long getUserId();

    String getSeries();

    String getNumber();

    String getOldSeries();

    String getOldNumber();

    LocalDateTime getRegistrationTime();

    Long getOrganId();

    Long getDepartmentId();

    Long getRegionId();

    Long getDistrictId();

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

    String getExplanatory();

    String getFabula();

    boolean getIsMain();

    boolean getIsDeleted();

}
