package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.ActorDetailRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class EmploymentDataValidator implements ConstraintValidator<ValidEmploymentData, ActorDetailRequestDTO> {

    private final ValidationService validationService;

    @Override
    public void initialize(ValidEmploymentData constraintAnnotation) {
    }

    @Override
    public boolean isValid(ActorDetailRequestDTO actorDetailRequestDTO, ConstraintValidatorContext context) {

        if (actorDetailRequestDTO == null || actorDetailRequestDTO.getOccupation() == null) {
            return true;
        }

        boolean isValid = true;

        if (!validationService.checkEmploymentPlace(actorDetailRequestDTO.getOccupation(), actorDetailRequestDTO.getEmploymentPlace())) {
            context.buildConstraintViolationWithTemplate(ErrorCode.VIOLATOR_EMPLOYMENT_PLACE_REQUIRED).addConstraintViolation();
            isValid = false;
        }

        if (!validationService.checkEmploymentPosition(actorDetailRequestDTO.getOccupation(), actorDetailRequestDTO.getEmploymentPosition())) {
            context.buildConstraintViolationWithTemplate(ErrorCode.VIOLATOR_EMPLOYMENT_POSITION_REQUIRED).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
