package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@RequiredArgsConstructor
public class PersonRequiredValidator implements ConstraintValidator<PersonRequired, ActorRequest> {

    @Override
    public void initialize(PersonRequired constraintAnnotation) {
    }

    @Override
    public boolean isValid(ActorRequest actorRequestDTO, ConstraintValidatorContext context) {
        if (actorRequestDTO == null) {
            return true;
        }
        return Boolean.logicalXor(
                Objects.isNull(actorRequestDTO.getDocument()),
                Objects.isNull(actorRequestDTO.getPinpp())
        );

    }
}
