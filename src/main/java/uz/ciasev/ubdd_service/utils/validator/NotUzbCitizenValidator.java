package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotUzbCitizenValidator implements ConstraintValidator<NotUzbCitizen, PersonRequestDTO> {

    @Override
    public void initialize(NotUzbCitizen constraintAnnotation) {}

    @Override
    public boolean isValid(PersonRequestDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getCitizenshipType() == null)
            return true;

        CitizenshipType ct = value.getCitizenshipType();

        return ct.not(CitizenshipTypeAlias.UZBEK);
    }
}
