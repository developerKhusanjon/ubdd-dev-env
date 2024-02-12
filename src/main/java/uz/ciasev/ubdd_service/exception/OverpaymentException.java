package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class OverpaymentException extends ApplicationException {

    public OverpaymentException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVOICE_OVERPAYMENT);
    }
}
