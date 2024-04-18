package uz.ciasev.ubdd_service.entity.webhook.ibd;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IBDWebhookProtocolDecisionProjection {

    Long getProtocolId();

    Long getAdmCaseId();

    Long getCaseStatusId();

    String getCaseStatusName();

    String getProtocolSeries();

    String getProtocolNumber();

    LocalDateTime getCreatedTime();

    String getOrganValue();

    String getInspectorFio();

    String getInspectorPinpp();

    Boolean getIsRealInspectorPinpp();

    String getFabula();

    LocalDateTime getViolationTime();

    String getRegionValue();

    String getDistrictValue();

    String getMtpValue();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    LocalDate getViolatorBirthDate();

    LocalDate getViolatorDocumentGivenDate();

    LocalDate getViolatorDocumentExpireDate();

    Long getViolatorDocumentGivenAddressId();

    String getViolatorGenderName();

    String getViolatorOccupationName();

    String getViolatorLastNameLat();

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorFirstNameKir();

    String getViolatorLastNameKir();

    String getViolatorSecondNameKir();

    String getViolatorPinpp();

    Boolean getViolatorIsRealPinpp();

    Long getViolatorBirthAddressId();

    Long getViolatorResidenceAddressId();

    String getViolatorEmploymentPosition();

    String getViolatorEmploymentPlace();

    String getViolatorMobile();
    String getViolatorPhotoUri();

    String getMainPunishmentAmount();

    Long getGenderId();

    Long getDocumentTypeId();

    Long getNationalityTypeId();

    Long getOccupationId();

    Long getCitizenshipTypeId();

    Long getRegionId();

    Long getDistrictId();

    Long getMtpId();

    Long getRegisteredOrganId();

    Long getMainPunishmentTypeId();

    Long getOrganRegionId();

    Long getOrganDistrictId();
    Long getDecisionId();

    default String getFormattedInspectorPinpp() {
        if (Boolean.TRUE.equals(this.getIsRealInspectorPinpp())) {
            return this.getInspectorPinpp();
        }
        return null;
    }

    default String getFormattedViolatorPinpp() {
        if (!this.getViolatorIsRealPinpp()) return null;

        return this.getViolatorPinpp();
    }
}
