package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ProperUbddDataBindValidator implements ConstraintValidator<ProperUbddDataBind, UbddDataBind> {

    @Override
    public void initialize(ProperUbddDataBind constraintAnnotation) {
    }

    @Override
    public boolean isValid(UbddDataBind ubddDataBind, ConstraintValidatorContext context) {

        List<Long> values = new LinkedList<>();

        values.add(ubddDataBind.getUbddDrivingLicenseDataId());
        values.add(ubddDataBind.getUbddTexPassDataId());
        values.add(ubddDataBind.getUbddTintingDataId());
        values.add(ubddDataBind.getUbddInsuranceDataId());
        values.add(ubddDataBind.getVehicleArrestId());
        values.add(ubddDataBind.getUbddAttorneyLetterDataId());

        return values.stream().anyMatch(Objects::nonNull);
    }
}
