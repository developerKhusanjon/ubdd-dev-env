package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.DamageTypeAlias.MATERIAL;
import static uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias.*;

public class DamageDetailValidator implements ConstraintValidator<ValidDamageDetail, DamageCreateRequestDTO> {

    private final EnumMap<VictimTypeAlias, Validator<DamageCreateRequestDTO>> victimTypeValidationMap = new EnumMap<>(VictimTypeAlias.class);

    @Override
    public void initialize(ValidDamageDetail constraintAnnotation) {
        victimTypeValidationMap.put(GOVERNMENT, this::validateGovernment);
        victimTypeValidationMap.put(VICTIM, this::validateVictim);
        victimTypeValidationMap.put(JURIDIC, this::validateJuridic);
    }

    @Override
    public boolean isValid(DamageCreateRequestDTO damageDetail, ConstraintValidatorContext context) {
        if (damageDetail == null) {
            return true;
        }
        if (damageDetail.getVictimType() == null) {
            return true;
        }
        if (damageDetail.getDamageType() == null) {
            return true;
        }
        return Optional.ofNullable(damageDetail.getVictimType())
                .map(VictimType::getAlias)
                .map(victimTypeValidationMap::get)
                .map(validator -> validator.isValid(damageDetail, context))
                .orElse(false);
    }

    private boolean validateVictim(DamageCreateRequestDTO damageDetail, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean isValid = true;

        if (Objects.isNull(damageDetail.getVictimId())) {
            isValid = false;
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.VICTIM_ID_REQUIRED)
                    .addConstraintViolation();
        }

        return isValid;
    }

    private boolean validateGovernment(DamageCreateRequestDTO damageDetail, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean isValid = true;

        if (Objects.nonNull(damageDetail.getVictimId())) {
            isValid = false;
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.VICTIM_ID_MUST_BE_EMPTY)
                    .addConstraintViolation();
        }

        if (damageDetail.getDamageType().not(MATERIAL)) {
            isValid = false;
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.DAMAGE_TYPE_MUST_BE_MATERIAL)
                    .addConstraintViolation();
        }

        return isValid;
    }

    private boolean validateJuridic(DamageCreateRequestDTO damageDetail, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean isValid = true;

        if (Objects.nonNull(damageDetail.getVictimId())) {
            isValid = false;
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.VICTIM_ID_MUST_BE_EMPTY)
                    .addConstraintViolation();
        }

        if (damageDetail.getDamageType().not(MATERIAL)) {
            isValid = false;
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.DAMAGE_TYPE_MUST_BE_MATERIAL)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
