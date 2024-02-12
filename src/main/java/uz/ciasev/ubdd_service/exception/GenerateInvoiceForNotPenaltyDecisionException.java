package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class GenerateInvoiceForNotPenaltyDecisionException extends ApplicationException {

    public GenerateInvoiceForNotPenaltyDecisionException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.GENERATE_INVOICE_FOR_NOT_PENALTY_DECISION);
    }
}
