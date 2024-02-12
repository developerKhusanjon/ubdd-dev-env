package uz.ciasev.ubdd_service.exception;

public class AdmCaseNotSingleViolatorException extends ValidationException {

    public AdmCaseNotSingleViolatorException() {
        super(ErrorCode.ADM_CASE_NOT_SINGLE);
    }
}
