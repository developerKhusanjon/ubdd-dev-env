package uz.ciasev.ubdd_service.dto.internal.request.resolution.court;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.*;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourtPunishmentRequestDTO implements PunishmentRequestDTO {

    @NotNull(message = ErrorCode.PUNISHMENT_TYPE_ID_REQUIRED)
    private PunishmentType punishmentType;

    //  2022-09-14 Бегзод сказал принимтаь от суда конфискацию и изьятие с нулевой суммой
    @MoneyAmount(message = ErrorCode.PUNISHMENT_AMOUNT_INVALID, required = false, allowZero = true)
    private Long amount;

    @Min(value = 0, message = ErrorCode.YEARS_LESS_THEN_0)
    private Integer years;

    @Min(value = 0, message = ErrorCode.MONTH_LESS_THEN_0)
    private Integer months;

    @Min(value = 0, message = ErrorCode.DAYS_LESS_THEN_0)
    private Integer days;

    @Min(value = 0, message = ErrorCode.HOURS_LESS_THEN_0)
    private Integer hours;

    @Min(value = 0, message = ErrorCode.DAYS_LESS_THEN_0)
    private Integer arrestDate;

    @Builder.Default
    private Boolean isDiscount70 = false;
    @Builder.Default
    private Boolean isDiscount50 = false;
    @Builder.Default
    private LocalDate discount70ForDate = null;
    @Builder.Default
    private Long discount70Amount = null;
    @Builder.Default
    private LocalDate discount50ForDate = null;
    @Builder.Default
    private Long discount50Amount = null;

    public void setDiscount(Long discount50Amount, LocalDate discount50ForDate, Long discount70Amount, LocalDate discount70ForDate) {
        this.isDiscount70 = true;
        this.isDiscount50 = true;
        this.discount70ForDate = discount70ForDate;
        this.discount70Amount = discount70Amount;
        this.discount50ForDate = discount50ForDate;
        this.discount50Amount = discount50Amount;
    }

    @Override
    public Integer getDays() {
        if (this.days != null) {
            return days;
        }
        if (this.arrestDate != null) {
            return arrestDate;
        }

        return null;
    }

    @Override
    public Punishment buildPunishment() {
        Punishment punishment = new Punishment();
        punishment.setType(this.punishmentType);

        switch (punishmentType.getAlias()) {
            case PENALTY: {
                PenaltyPunishment punishmentDetail = new PenaltyPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount);
                punishmentDetail.setIsDiscount70(this.isDiscount70);
                punishmentDetail.setIsDiscount50(this.isDiscount50);
                punishmentDetail.setDiscount70ForDate(this.discount70ForDate);
                punishmentDetail.setDiscount70Amount(this.discount70Amount);
                punishmentDetail.setDiscount50ForDate(this.discount50ForDate);
                punishmentDetail.setDiscount50Amount(this.discount50Amount);

                punishment.setPenalty(punishmentDetail);
                break;
            }
            case WITHDRAWAL: {
                WithdrawalPunishment punishmentDetail = new WithdrawalPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount);
                punishment.setWithdrawal(punishmentDetail);
                break;
            }
            case CONFISCATION: {
                ConfiscationPunishment punishmentDetail = new ConfiscationPunishment();
                punishmentDetail.setPunishment(punishment);
                punishmentDetail.setAmount(this.amount);
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
                punishmentDetail.setDays(this.arrestDate);
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
