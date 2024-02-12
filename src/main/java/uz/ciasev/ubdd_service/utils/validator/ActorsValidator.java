package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ActorsValidator implements ConstraintValidator<ValidActors, ActorRequest> {

    @Override
    public void initialize(ValidActors constraintAnnotation) {
    }

    @Override
    public boolean isValid(ActorRequest value, ConstraintValidatorContext context) {
        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        boolean isPinppPresent = value.getPinpp() != null;
        boolean isPersonPresent = value.getPerson() != null;
        boolean isDocumentPresent = value.getDocument() != null;

        if (isPinppPresent) {
            if(value.getPinpp().length() != 14) {
                context
                        .buildConstraintViolationWithTemplate(ErrorCode.PINPP_INVALID)
                        .addConstraintViolation();
                return false;
            }
        }

        if (isPinppPresent && isPersonPresent) {
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.PERSON_SHOULD_BE_EMPTY_FOR_FILLED_PINPP)
//                    .buildConstraintViolationWithTemplate(ErrorCode.PROVIDE_ONLY_PINPP_OR_MANUAL_PERSON_DATA)
                    .addConstraintViolation();
            isValid = false;
        }


        if (!isPinppPresent) {
            if (!isPersonPresent || !isDocumentPresent) {
                context
                        .buildConstraintViolationWithTemplate(ErrorCode.PERSON_AND_DOCUMENT_DATA_REQUIRED_FOR_EMPTY_PINPP)
                        .addConstraintViolation();
                isValid = false;
            }
        }

        isValid = isValid && validatePersonDocumentType(context, value);

        return isValid;
    }


    boolean validatePersonDocumentType(ConstraintValidatorContext context, ActorRequest value) {
        if (value.getDocument() == null || value.getDocument().getPersonDocumentType() == null) {
            return true;
        }

        PersonDocumentType personDocumentType = value.getDocument().getPersonDocumentType();

        if (personDocumentType.getIsActive()) {
            return true;

        }

        if (personDocumentType.notBiometric()) {
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.DOCUMENT_TYPE_DEACTIVATED)
                    .addConstraintViolation();
            return false;
        }

        if (value.getPinpp() == null) {
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.BIOMETRIC_DOCUMENT_TYPE_DEACTIVATED)
                    .addConstraintViolation();
            return false;
        }

        return true;

    }
}