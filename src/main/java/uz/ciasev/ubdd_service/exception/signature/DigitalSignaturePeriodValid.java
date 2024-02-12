package uz.ciasev.ubdd_service.exception.signature;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class DigitalSignaturePeriodValid extends ApplicationException {

    public DigitalSignaturePeriodValid() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.SIGNATURE_CERTIFICATE_PERIOD_VALID);
    }
}
