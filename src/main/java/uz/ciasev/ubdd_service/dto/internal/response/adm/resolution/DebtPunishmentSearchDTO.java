package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.DebtPunishmentSearchProjection;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

@Data
@EqualsAndHashCode(of = {"id"})
public class DebtPunishmentSearchDTO {

    private Long id;
    private String series;
    private String number;
    private Long articleViolationTypeId;
    private Long decisionStatusId;
    private Long punishmentStatusId;
    private String violatorFirstNameLat;
    private String violatorSecondNameLat;
    private String violatorLastNameLat;
    private Long articlePartId;
    private LocalDateTime resolutionTime;
    private Long resolutionRegionId;
    private Long resolutionDistrictId;
    private LocalDate executionFromDate;
    private String mainPunishmentAmountText;
    private Long mainPunishmentAmount;
    private Long punishmentDiscountAmount;
    private LocalDate punishmentDiscountForDate;
    private Long punishmentDiscount70Amount;
    private LocalDate punishmentDiscount70ForDate;
    private Long punishmentDiscount50Amount;
    private LocalDate punishmentDiscount50ForDate;
    private Long punishmentPaidAmount;
    private LocalDateTime punishmentLastPayTime;
    private Long punishmentId;
    private Boolean isInvoiceActive;
    private String invoiceSerial;
    private Boolean isOverdue;
    private Boolean isInMib;
    private String mibCaseNumber;
    private String vehicleNumber;
    private PunishmentTypeAlias punishmentType;
    private Long punishmentTypeId;
    private LocalDate licenseRevocationEndDate;

    private ApiUrl decisionPdf;

    public static DebtPunishmentSearchDTO of(DebtPunishmentSearchProjection projection) {

        DebtPunishmentSearchDTO rsl = new DebtPunishmentSearchDTO();

        rsl.setId(projection.getId());
        rsl.setSeries(projection.getSeries());
        rsl.setNumber(projection.getNumber());
        rsl.setArticleViolationTypeId(projection.getArticleViolationTypeId());
        rsl.setDecisionStatusId(projection.getDecisionStatusId());
        rsl.setPunishmentStatusId(projection.getPunishmentStatusId());
        rsl.setViolatorFirstNameLat(projection.getViolatorFirstNameLat());
        rsl.setViolatorSecondNameLat(projection.getViolatorSecondNameLat());
        rsl.setViolatorLastNameLat(projection.getViolatorLastNameLat());
        rsl.setArticlePartId(projection.getArticlePartId());
        rsl.setResolutionTime(projection.getResolutionTime());
        rsl.setResolutionRegionId(projection.getResolutionRegionId());
        rsl.setResolutionDistrictId(projection.getResolutionDistrictId());
        rsl.setExecutionFromDate(projection.getExecutionFromDate());
        rsl.setMainPunishmentAmountText(projection.getMainPunishmentAmountText());
        rsl.setInvoiceSerial(projection.getInvoiceSerial());
        rsl.setIsInvoiceActive(projection.getIsInvoiceActive());

        rsl.setIsOverdue(!projection.getExecutionFromDate().plusDays(61).isAfter(LocalDate.now()));
        rsl.setIsInMib(projection.getDecisionStatusId().equals(15L));

        rsl.setMibCaseNumber(projection.getMibCaseNumber());
        rsl.setVehicleNumber(projection.getVehicleNumber());

        rsl.decisionPdf = projection.getDecisionPdf();

        rsl.setMainPunishmentAmount(projection.getMainPunishmentAmount());
        rsl.setPunishmentDiscountAmount(projection.getPunishmentDiscount70Amount());
        rsl.setPunishmentDiscountForDate(projection.getPunishmentDiscount70ForDate());
        rsl.setPunishmentDiscount70Amount(projection.getPunishmentDiscount70Amount());
        rsl.setPunishmentDiscount70ForDate(projection.getPunishmentDiscount70ForDate());
        rsl.setPunishmentDiscount50Amount(projection.getPunishmentDiscount50Amount());
        rsl.setPunishmentDiscount50ForDate(projection.getPunishmentDiscount50ForDate());
        rsl.setPunishmentPaidAmount(projection.getPunishmentPaidAmount());
        rsl.setPunishmentLastPayTime(projection.getPunishmentLastPayTime());
        rsl.setPunishmentId(projection.getPunishmentId());

        rsl.setPunishmentType(projection.getPunishmentType());
        rsl.setPunishmentTypeId(projection.getPunishmentTypeId());
        rsl.setLicenseRevocationEndDate(projection.getLicenseRevocationEndDate());

        return rsl;
    }

    public static DebtPunishmentSearchDTO of(DebtPunishmentSearchProjection projection, Function<Long, String> getMibCaseNumber) {

        DebtPunishmentSearchDTO rsl = DebtPunishmentSearchDTO.of(projection);

        if (rsl.getIsInMib()) {
            rsl.mibCaseNumber = getMibCaseNumber.apply(projection.getId());
        }

        return rsl;
    }
}
