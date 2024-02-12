package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PunishmentValidationServiceImpl implements PunishmentValidationService {

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
    private final EnumMap<PunishmentTypeAlias, PunishmentValidator> punishmentTypeValidatorMap = new EnumMap<>(PunishmentTypeAlias.class);

    {
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.PENALTY, this::validateAmountPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.CONFISCATION, this::validateAmountPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.WITHDRAWAL, this::validateAmountPunishment);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.LICENSE_REVOCATION, this::validateLicenseRevocation);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.ARREST, this::validateArrest);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.DEPORTATION, this::validateDeportation);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.COMMUNITY_WORK, this::validateCommunityWork);
        punishmentTypeValidatorMap.put(PunishmentTypeAlias.MEDICAL_PENALTY, this::validateMedicalPenalty);
    }

    @Override
    public void validatePunishment(ValidationCollectingError error,
                                   PunishmentRequestDTO punishmentRequestDTO) {
        PunishmentValidator validator = this.punishmentTypeValidatorMap.get(punishmentRequestDTO.getPunishmentType().getAlias());
        validator.validate(error, punishmentRequestDTO);
    }

    private void validateLicenseRevocation(ValidationCollectingError error, PunishmentRequestDTO punishment) {
        int years = Optional.ofNullable(punishment.getYears()).orElse(0);
        int months = Optional.ofNullable(punishment.getMonths()).orElse(0);
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        error.addIf(days > MAX_DAYS, "DAYS_MORE_THEN_31");
        error.addIf(months > MAX_MONTHS, "MONTH_MORE_THEN_12");
        error.addIf(years >= MAX_LICENSE_REVOCATION_YEARS && (days + months) != 0, "PUNISHMENT_TERM_MORE_THEN_3_YEARS");
        error.addIf(days < MIN_LICENSE_REVOCATION_DAYS && (years + months) == 0, "PUNISHMENT_TERM_LESS_THEN_15_DAYS");

        error.addIf(Objects.nonNull(punishment.getHours()), "HOURS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getAmount()), "AMOUNT_NOT_ALLOW");
    }


    private void validateArrest(ValidationCollectingError error, PunishmentRequestDTO punishment) {
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        error.addIf(days > MAX_ARREST_DAYS, "PUNISHMENT_TERM_MORE_THEN_31_DAYS");
        error.addIf(days < MIN_ARREST_DAYS, "PUNISHMENT_TERM_LESS_THEN_3_DAY");

        error.addIf(Objects.nonNull(punishment.getYears()), "YEARS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getMonths()), "MONTHS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getHours()), "HOURS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getAmount()), "AMOUNT_NOT_ALLOW");
    }

    private void validateDeportation(ValidationCollectingError error, PunishmentRequestDTO punishment) {

        error.addIf(Objects.nonNull(punishment.getYears()), "YEARS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getMonths()), "MONTHS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getDays()), "DAYS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getHours()), "HOURS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getAmount()), "AMOUNT_NOT_ALLOW");

    }


    private void validateCommunityWork(ValidationCollectingError error, PunishmentRequestDTO punishment) {
        int hours = Optional.ofNullable(punishment.getHours()).orElse(0);

        error.addIf(hours < MIN_COMMUNITY_WORK_HOURS, "PUNISHMENT_TERM_LESS_THEN_HOUR");
        error.addIf(hours > MAX_COMMUNITY_WORK_HOURS, "PUNISHMENT_TERM_MORE_THEN_1000000_HOURS");

        error.addIf(Objects.nonNull(punishment.getYears()), "YEARS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getMonths()), "MONTHS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getDays()), "DAYS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getAmount()), "AMOUNT_NOT_ALLOW");
    }


    private void validateMedicalPenalty(ValidationCollectingError error, PunishmentRequestDTO punishment) {
        int days = Optional.ofNullable(punishment.getDays()).orElse(0);

        error.addIf(days < MIN_MEDICAL_PENALTY_DAYS, "PUNISHMENT_TERM_LESS_THEN_DAY");
        error.addIf(days > MAX_MEDICAL_PENALTY_DAYS, "PUNISHMENT_TERM_MORE_THEN_30_DAYS");

        error.addIf(Objects.nonNull(punishment.getYears()), "YEARS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getMonths()), "MONTHS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getHours()), "HOURS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getAmount()), "AMOUNT_NOT_ALLOW");
    }


    private void validateAmountPunishment(ValidationCollectingError error, PunishmentRequestDTO punishment) {
        error.addIf(Objects.isNull(punishment.getAmount()), "AMOUNT_REQUIRED");

        error.addIf(Objects.nonNull(punishment.getYears()), "YEARS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getMonths()), "MONTHS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getDays()), "DAYS_NOT_ALLOW");
        error.addIf(Objects.nonNull(punishment.getHours()), "HOURS_NOT_ALLOW");
    }

    @FunctionalInterface
    public interface PunishmentValidator {
        void validate(ValidationCollectingError error, PunishmentRequestDTO punishmentRequestDTO);
    }
}
