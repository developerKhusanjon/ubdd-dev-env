package uz.ciasev.ubdd_service.entity.court.projection;

import java.time.LocalDateTime;

public interface CourtProtocolProjection {

    Long getId();
    LocalDateTime getCreatedTime();
    Long getMtpId();
    Long getCourtDistrictId();
    Boolean getIsJuridic();
    String getJuridicInn();
    String getJuridicOrganizationName();
    String getViolatorDocumentSeries();
    String getViolatorDocumentNumber();
    Long getViolatorDocumentTypeId();
    Long getOccupationId();
    String getEmploymentPlace();
    String getEmploymentPosition();
}
