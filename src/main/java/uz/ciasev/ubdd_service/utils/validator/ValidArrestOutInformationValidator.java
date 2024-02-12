package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.execution.ArrestExecutionRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidArrestOutInformationValidator implements ConstraintValidator<ValidArrestOutInformation, ArrestExecutionRequestDTO> {

    @Override
    public void initialize(ValidArrestOutInformation constraintAnnotation) {
    }

    @Override
    public boolean isValid(ArrestExecutionRequestDTO requestDTO, ConstraintValidatorContext context) {
        return !(
                requestDTO.getOutDate() != null ^
                (requestDTO.getOutState() != null && requestDTO.getOutState().trim().length() > 0)
        );
    }
}
