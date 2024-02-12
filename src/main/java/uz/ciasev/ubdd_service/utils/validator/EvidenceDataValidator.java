package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static uz.ciasev.ubdd_service.exception.ErrorCode.EVIDENCE_CATEGORY_MONEY_CURRENCY_AND_COST_REQUIRED;
import static uz.ciasev.ubdd_service.exception.ErrorCode.EVIDENCE_CATEGORY_NO_MONEY_MEASURE_AND_QUANTITY_REQUIRED;

public class EvidenceDataValidator implements ConstraintValidator<ValidEvidenceData, EvidenceData> {

    @Override
    public void initialize(ValidEvidenceData constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EvidenceData evidence, ConstraintValidatorContext context) {
        if (evidence == null || evidence.getEvidenceCategory() == null)
            return true;

        var isValid = true;
        var evidenceCategory = evidence.getEvidenceCategory();
        if (evidenceCategory == null) {
            return true;
        }

        if (evidenceCategory.getIsMoney()) {
            if (evidence.getCurrency() == null || evidence.getCost() == null) {
                context.buildConstraintViolationWithTemplate(EVIDENCE_CATEGORY_MONEY_CURRENCY_AND_COST_REQUIRED)
                        .addConstraintViolation();
                isValid = false;
            }
        } else {
            if (evidence.getMeasure() == null || evidence.getQuantity() == null) {
                context.buildConstraintViolationWithTemplate(EVIDENCE_CATEGORY_NO_MONEY_MEASURE_AND_QUANTITY_REQUIRED)
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
