package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class MtpWithCodeAlreadyExistsInDistrictException extends ValidationException {
    public MtpWithCodeAlreadyExistsInDistrictException(String code, Long districtId) {
        super(
                ErrorCode.MTP_WITH_CODE_ALREADY_EXISTS_IN_DISTRICT,
                String.format(
                        "Mtp with code '%s' already exist in district '%s'",
                        code,
                        districtId
                )
        );
    }
}
