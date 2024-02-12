package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CitizenshipTypeCalculationUnacceptable extends ApplicationException {

    public CitizenshipTypeCalculationUnacceptable() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CITIZENSHIP_TYPE_CALCULATION_UNACCEPTABLE);
    }
}
