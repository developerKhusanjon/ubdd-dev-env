package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.*;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class OrganPunishmentRequestDTO implements PunishmentRequestDTO {

    @NotNull(message = ErrorCode.PUNISHMENT_TYPE_ID_REQUIRED)
    @ActiveOnly(message = ErrorCode.PUNISHMENT_TYPE_DEACTIVATED)
    @JsonProperty(value = "punishmentTypeId")
    private PunishmentType punishmentType;

    @MoneyAmount(message = ErrorCode.PUNISHMENT_AMOUNT_INVALID, required = false)
    private Long amount;

    @Min(value = 0, message = ErrorCode.YEARS_LESS_THEN_0)
    private Integer years;

    @Min(value = 0, message = ErrorCode.MONTH_LESS_THEN_0)
    private Integer months;

    @Min(value = 0, message = ErrorCode.DAYS_LESS_THEN_0)
    private Integer days;

    @Min(value = 0, message = ErrorCode.HOURS_LESS_THEN_0)
    private Integer hours;

    private LocalDate discount50ForDate;

    private Long discount50Amount;

    private LocalDate discount70ForDate;

    private Long discount70Amount;

    public Punishment buildPunishment() {
        Punishment punishment = new Punishment();
        punishment.setType(this.punishmentType);

        switch (punishmentType.getAlias()) {
            case PENALTY: {
                PenaltyPunishment punishmentDetail = new PenaltyPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount * 100);
                punishmentDetail.setDiscount50Amount(this.discount50Amount * 100);
                punishmentDetail.setDiscount50ForDate(this.discount50ForDate);
                punishmentDetail.setDiscount70Amount(this.discount70Amount * 100);
                punishmentDetail.setDiscount70ForDate(this.discount70ForDate);
                punishment.setPenalty(punishmentDetail);
                break;
            }
            case WITHDRAWAL: {
                WithdrawalPunishment punishmentDetail = new WithdrawalPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount * 100);
                punishment.setWithdrawal(punishmentDetail);
                break;
            }
            case CONFISCATION: {
                ConfiscationPunishment punishmentDetail = new ConfiscationPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount * 100);
                punishment.setConfiscation(punishmentDetail);
                break;
            }
            case LICENSE_REVOCATION: {
                LicenseRevocationPunishment punishmentDetail = new LicenseRevocationPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setDays(this.days == null ? 0 : this.days);
                punishmentDetail.setMonths(this.months == null ? 0 : this.months);
                punishmentDetail.setYears(this.years == null ? 0 : this.years);
                punishment.setLicenseRevocation(punishmentDetail);
                break;
            }
            case ARREST: {
                ArrestPunishment punishmentDetail = new ArrestPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setDays(this.days);
                punishment.setArrest(punishmentDetail);
                break;
            }
            case DEPORTATION: {
                DeportationPunishment punishmentDetail = new DeportationPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setDays(this.days == null ? 0 : this.days);
                punishmentDetail.setMonths(this.months == null ? 0 : this.months);
                punishmentDetail.setYears(this.years == null ? 0 : this.years);
                punishment.setDeportation(punishmentDetail);
                break;
            }
            case COMMUNITY_WORK: {
                CommunityWorkPunishment punishmentDetail = new CommunityWorkPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setHours(this.hours);
                punishment.setCommunityWork(punishmentDetail);
                break;
            }
            case MEDICAL_PENALTY: {
                MedicalPunishment punishmentDetail = new MedicalPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setDays(this.days);
                punishment.setMedical(punishmentDetail);
                break;
            }
        }

        return punishment;
    }
}
