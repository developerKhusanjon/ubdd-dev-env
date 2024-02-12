package uz.ciasev.ubdd_service.exception;


public class AdmCaseAlreadySendException extends ValidationException {

    public AdmCaseAlreadySendException() {
        super(ErrorCode.ADM_CASE_ALREADY_SEND);
    }
}
