package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.entity.dict.DocumentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AttachableDocumentValidator implements ConstraintValidator<AttachableDocument, DocumentType> {

    @Override
    public void initialize(AttachableDocument constraintAnnotation) {
    }

    @Override
    public boolean isValid(DocumentType documentType, ConstraintValidatorContext context) {

        if (documentType == null) {
            return true;
        }
        return !documentType.getPreventAttach();
    }
}
