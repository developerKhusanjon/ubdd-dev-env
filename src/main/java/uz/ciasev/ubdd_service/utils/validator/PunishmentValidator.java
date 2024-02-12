package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
public abstract class PunishmentValidator<T extends PunishmentRequestDTO> implements ConstraintValidator<ValidPunishment, T> {


    public static final int MAX_MONTHS = 12;
    public static final int MAX_DAYS = 31;
    public static final int MIN_ARREST_DAYS = 3;
    public static final int MAX_ARREST_DAYS = 31;
    public static final int MIN_MEDICAL_PENALTY_DAYS = 1;
    public static final int MAX_MEDICAL_PENALTY_DAYS = 30;
    public static final int MIN_DEPORTATION_YEARS = 1;
    public static final int MAX_DEPORTATION_YEARS = 3;
    public static final int MIN_LICENSE_REVOCATION_DAYS = 3;
    public static final int MAX_LICENSE_REVOCATION_YEARS = 3;
    public static final int MIN_COMMUNITY_WORK_HOURS = 8;
    public static final int MAX_COMMUNITY_WORK_HOURS = 240;
    private final EnumMap<PunishmentTypeAlias, Validator<T>> punishmentTypeValidatorMap = new EnumMap<>(PunishmentTypeAlias.class);

    {
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.PENALTY, this::validatePenaltyPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.CONFISCATION, this::validateConfiscationPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.WITHDRAWAL, this::validateWithdrawalPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.LICENSE_REVOCATION, this::validateLicenseRevocation);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.ARREST, this::validateArrest);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.DEPORTATION, this::validateDeportation);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.COMMUNITY_WORK, this::validateCommunityWork);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.MEDICAL_PENALTY, this::validateMedicalPenalty);
    }

    @Override
    public boolean isValid(T punishmentRequestDTO, ConstraintValidatorContext context) {
        if (punishmentRequestDTO == null) {
            return true;
        }

        if (punishmentRequestDTO.getPunishmentType() == null) {
            return true;
        }

        Validator<T> validator = this.punishmentTypeValidatorMap.get(punishmentRequestDTO.getPunishmentType().getAlias());
        return validator.isValid(punishmentRequestDTO, context);
    }

    protected boolean validateLicenseRevocation(T punishment, ConstraintValidatorContext context) {
        int years = Optional.ofNullable(punishment.getYears()).orElse(0);
        int months = Optional.ofNullable(punishment.getMonths()).orElse(0);
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        ValidationHelper helper = new ValidationHelper(context);

        if (days > MAX_DAYS) {
            helper.accept(ErrorCode.DAYS_MORE_THEN_31);
        }
        if (months > MAX_MONTHS) {
            helper.accept(ErrorCode.MONTH_MORE_THEN_12);
        }
        if (years >= MAX_LICENSE_REVOCATION_YEARS && (days + months) != 0) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_MORE_THEN_3_YEARS);
        }
        if (days < MIN_LICENSE_REVOCATION_DAYS && (years + months) == 0) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_LESS_THEN_3_DAYS);
        }

        if (Objects.nonNull(punishment.getHours())) {
            helper.accept(ErrorCode.HOURS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_NOT_ALLOW);
        }

        return helper.isValid();
    }


    protected boolean validateArrest(T punishment, ConstraintValidatorContext context) {
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        ValidationHelper helper = new ValidationHelper(context);

        if (days > MAX_DAYS) {
            helper.accept(ErrorCode.DAYS_MORE_THEN_31);
        }
        if (days > MAX_ARREST_DAYS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_MORE_THEN_31_DAYS);
        }
        if (days < MIN_ARREST_DAYS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_LESS_THEN_DAY);
        }

        if (Objects.nonNull(punishment.getYears())) {
            helper.accept(ErrorCode.YEARS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getMonths())) {
            helper.accept(ErrorCode.MONTHS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getHours())) {
            helper.accept(ErrorCode.HOURS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_NOT_ALLOW);
        }

        return helper.isValid();
    }

    protected boolean validateDeportation(T punishment, ConstraintValidatorContext context) {
        int years = Optional.ofNullable(punishment.getYears()).orElse(0);
        int months = Optional.ofNullable(punishment.getMonths()).orElse(0);
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        ValidationHelper helper = new ValidationHelper(context);

        if (days > MAX_DAYS) {
            helper.accept(ErrorCode.DAYS_MORE_THEN_31);
        }
        if (months > MAX_MONTHS) {
            helper.accept(ErrorCode.MONTH_MORE_THEN_12);
        }
        if (years < MIN_DEPORTATION_YEARS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_LESS_THEN_YEARS);
        }
        if (years >= MAX_DEPORTATION_YEARS && (days + months) != 0) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_MORE_THEN_3_YEARS);
        }

        if (Objects.nonNull(punishment.getHours())) {
            helper.accept(ErrorCode.HOURS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_NOT_ALLOW);
        }

        return helper.isValid();
    }


    protected boolean validateCommunityWork(T punishment, ConstraintValidatorContext context) {
        int hours = Optional.ofNullable(punishment.getHours()).orElse(0);

        ValidationHelper helper = new ValidationHelper(context);

        if (hours < MIN_COMMUNITY_WORK_HOURS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_LESS_THEN_8_HOUR);
        }
        if (hours > MAX_COMMUNITY_WORK_HOURS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_MORE_THEN_240_HOURS);
        }

        if (Objects.nonNull(punishment.getYears())) {
            helper.accept(ErrorCode.YEARS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getMonths())) {
            helper.accept(ErrorCode.MONTHS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getDays())) {
            helper.accept(ErrorCode.DAYS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_NOT_ALLOW);
        }

        return helper.isValid();
    }


    protected boolean validateMedicalPenalty(T punishment, ConstraintValidatorContext context) {
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        ValidationHelper helper = new ValidationHelper(context);

        if (days < MIN_MEDICAL_PENALTY_DAYS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_LESS_THEN_DAY);
        }
        if (days > MAX_MEDICAL_PENALTY_DAYS) {
            helper.accept(ErrorCode.PUNISHMENT_TERM_MORE_THEN_30_DAYS);
        }

        if (Objects.nonNull(punishment.getYears())) {
            helper.accept(ErrorCode.YEARS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getMonths())) {
            helper.accept(ErrorCode.MONTHS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getHours())) {
            helper.accept(ErrorCode.HOURS_NOT_ALLOW);
        }
        if (Objects.nonNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_NOT_ALLOW);
        }

        return helper.isValid();
    }

    protected boolean validateWithdrawalPunishment(T punishmentRequestDTO, ConstraintValidatorContext constraintValidatorContext) {
        return validateAmountPunishment(punishmentRequestDTO, constraintValidatorContext);
    }

    protected boolean validateConfiscationPunishment(T punishmentRequestDTO, ConstraintValidatorContext constraintValidatorContext) {
        return validateAmountPunishment(punishmentRequestDTO, constraintValidatorContext);
    }

    protected boolean validatePenaltyPunishment(T punishmentRequestDTO, ConstraintValidatorContext constraintValidatorContext) {
        return validateAmountPunishment(punishmentRequestDTO, constraintValidatorContext);
    }


    private boolean validateAmountPunishment(T punishment, ConstraintValidatorContext context) {
        ValidationHelper helper = new ValidationHelper(context);

        if(Objects.isNull(punishment.getAmount())) {
            helper.accept(ErrorCode.AMOUNT_REQUIRED);
        }

        if(Objects.nonNull(punishment.getYears())) {
            helper.accept(ErrorCode.YEARS_NOT_ALLOW);
        }
        if(Objects.nonNull(punishment.getMonths())) {
            helper.accept(ErrorCode.MONTHS_NOT_ALLOW);
        }
        if(Objects.nonNull(punishment.getDays())) {
            helper.accept(ErrorCode.DAYS_NOT_ALLOW);
        }
        if(Objects.nonNull(punishment.getHours())) {
            helper.accept(ErrorCode.HOURS_NOT_ALLOW);
        }

        return helper.isValid();
    }
}
