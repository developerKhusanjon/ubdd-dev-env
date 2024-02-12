package uz.ciasev.ubdd_service.entity.resolution.punishment;

import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DebtPunishmentSearchProjection {

    Long getId();
    String getSeries();
    String getNumber();
    Long getArticleViolationTypeId();
    Long getDecisionStatusId();
    Long getPunishmentStatusId();
    String getViolatorFirstNameLat();
    String getViolatorSecondNameLat();
    String getViolatorLastNameLat();
    Long getArticlePartId();
    LocalDateTime getResolutionTime();
    Long getResolutionRegionId();
    Long getResolutionDistrictId();
    LocalDate getExecutionFromDate();
    String getMainPunishmentAmountText();
    Long getMainPunishmentAmount();
    Long getPunishmentDiscount70Amount();
    LocalDate getPunishmentDiscount70ForDate();
    Long getPunishmentDiscount50Amount();
    LocalDate getPunishmentDiscount50ForDate();
    Long getPunishmentPaidAmount();
    LocalDateTime getPunishmentLastPayTime();
    Long getPunishmentId();
    Boolean getIsInvoiceActive();
    String getInvoiceSerial();
    String getMibCaseNumber();
    String getVehicleNumber();
    PunishmentTypeAlias getPunishmentType();
    Long getPunishmentTypeId();
    LocalDate getLicenseRevocationEndDate();

    default ApiUrl getDecisionPdf() {
        return ApiUrl.getDecisionInstance(getId());
    }
}
