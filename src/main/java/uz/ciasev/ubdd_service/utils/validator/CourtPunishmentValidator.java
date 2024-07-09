package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidatorContext;

public class CourtPunishmentValidator extends PunishmentValidator<CourtPunishmentRequestDTO> {

    @Override
    protected boolean validatePenaltyPunishment(CourtPunishmentRequestDTO punishmentRequestDTO, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = super.validatePenaltyPunishment(punishmentRequestDTO, constraintValidatorContext);

        if (!isValid) {
            return false;
        }

        if (punishmentRequestDTO.getAmount() == 0) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(ErrorCode.AMOUNT_SIZE_SO_SMALL)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
