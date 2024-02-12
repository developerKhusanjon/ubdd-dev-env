package uz.ciasev.ubdd_service.service.search.decision;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionFullListProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DecisionFullListResponseDTO {

    private Long id;
    private Long decisionTypeId;

    private String series;
    private String number;

    private Long statusId;
    private Boolean isActive;

    private String violatorFirstNameLat;
    private String violatorSecondNameLat;
    private String violatorLastNameLat;
    private Long violatorNationalityId;
    private Long violatorGenderId;

    private Long articlePartId;
    private Long articleViolationTypeId;

    private Long organId;
    private Long departmentId;
    private Long regionId;
    private Long districtId;

    private LocalDateTime resolutionTime;

    private Long mainPunishmentTypeId;
    private Long additionalPunishmentTypeId;

    private String mainPunishmentAmountText;
    private String additionalPunishmentAmountText;

    private LocalDate violatorBirthday;
    private String violatorPostAddress;
    private String violatorActualAddress;

    private Boolean isMibForceExecution;
    private Long mibTotalRecoveredAmount;

    private String penaltyInvoiceSerial;
    private Long penaltyDiscountAmount;
    private LocalDate penaltyDiscountForDate;
    private Long penaltyDiscount70Amount;
    private LocalDate penaltyDiscount70ForDate;
    private Long penaltyDiscount50Amount;
    private LocalDate penaltyDiscount50ForDate;
    private Long penaltyPaidAmount;
    private LocalDateTime penaltyLastPayTime;
    private Long govCompensationAmount;
    private Long govCompensationPaidAmount;

    public DecisionFullListResponseDTO(DecisionFullListProjection projection) {

        this.id = projection.getId();
        this.decisionTypeId = projection.getDecisionTypeId();

        this.series = projection.getSeries();
        this.number = projection.getNumber();

        this.statusId = projection.getStatusId();
        this.isActive = projection.getIsActive();

        this.violatorFirstNameLat = projection.getViolatorFirstNameLat();
        this.violatorSecondNameLat = projection.getViolatorSecondNameLat();
        this.violatorLastNameLat = projection.getViolatorLastNameLat();
        this.violatorNationalityId = projection.getViolatorNationalityId();
        this.violatorGenderId = projection.getViolatorGenderId();

        this.articlePartId = projection.getArticlePartId();
        this.articleViolationTypeId = projection.getArticleViolationTypeId();

        this.organId = projection.getOrganId();
        this.departmentId = projection.getDepartmentId();
        this.regionId = projection.getRegionId();
        this.districtId = projection.getDistrictId();

        this.resolutionTime = projection.getResolutionTime();

        this.mainPunishmentTypeId = projection.getMainPunishmentTypeId();
        this.additionalPunishmentTypeId = projection.getAdditionalPunishmentTypeId();

        this.mainPunishmentAmountText = projection.getMainPunishmentAmountText();
        this.additionalPunishmentAmountText = projection.getAdditionalPunishmentAmountText();

        this.violatorBirthday = projection.getViolatorBirthDate();
        this.violatorActualAddress = projection.getViolatorActualAddressText();
        this.violatorPostAddress = projection.getViolatorPostAddressText();

        this.isMibForceExecution = projection.getIsMibForceExecution();
        this.mibTotalRecoveredAmount = projection.getMibTotalRecoveredAmount();

        this.penaltyInvoiceSerial = projection.getPenaltyInvoiceSerial();

        if (Boolean.TRUE.equals(projection.penaltyIsDiscount70())) {
            this.penaltyDiscountAmount = projection.getPenaltyDiscount70Amount();
            this.penaltyDiscountForDate = projection.getPenaltyDiscount70ForDate();
            this.penaltyDiscount70Amount = projection.getPenaltyDiscount70Amount();
            this.penaltyDiscount70ForDate = projection.getPenaltyDiscount70ForDate();
        }

        if (Boolean.TRUE.equals(projection.penaltyIsDiscount50())) {
            this.penaltyDiscount50Amount = projection.getPenaltyDiscount50Amount();
            this.penaltyDiscount50ForDate = projection.getPenaltyDiscount50ForDate();
        }

        this.penaltyPaidAmount = projection.getPenaltyPaidAmount();
        this.penaltyLastPayTime = projection.getPenaltyLastPayTime();
        this.govCompensationAmount = projection.getGovCompensationAmount();
        this.govCompensationPaidAmount = projection.getGovCompensationPaidAmount();
    }
}
