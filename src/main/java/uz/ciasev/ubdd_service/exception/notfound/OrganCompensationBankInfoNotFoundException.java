package uz.ciasev.ubdd_service.exception.notfound;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class OrganCompensationBankInfoNotFoundException extends ApplicationException {

    public OrganCompensationBankInfoNotFoundException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.ORGAN_COMPENSATION_BANK_INFO_NOT_FOUND);
    }
}
