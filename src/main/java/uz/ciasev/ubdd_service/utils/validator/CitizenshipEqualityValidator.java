package uz.ciasev.ubdd_service.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.service.main.CitizenshipTypeCalculatingService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CitizenshipEqualityValidator implements ConstraintValidator<CitizenshipEquality, ActorRequest> {

    private final CitizenshipTypeCalculatingService citizenshipTypeService;

    @Autowired
    public CitizenshipEqualityValidator(CitizenshipTypeCalculatingService citizenshipTypeDictionaryService) {
        this.citizenshipTypeService = citizenshipTypeDictionaryService;
    }

    @Override
    public void initialize(CitizenshipEquality constraintAnnotation) {}

    @Override
    public boolean isValid(ActorRequest value, ConstraintValidatorContext context) {
        if (value == null
                || value.getPerson() == null
                || value.getPerson().getCitizenshipType() == null
                || value.getDocument() == null
                || value.getDocument().getPersonDocumentType() == null
                || value.getDocument().getGivenAddress() == null) {
            return true;
        }

        CitizenshipType ct = value.getPerson().getCitizenshipType();
        CitizenshipType ctDoc = citizenshipTypeService.calculate(value.getDocument());

        return ct.equals(ctDoc);
    }
}