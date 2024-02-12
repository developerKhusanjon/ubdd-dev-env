package uz.ciasev.ubdd_service.exception;

public class AdmCaseIsEmptyException extends ValidationException {

    public AdmCaseIsEmptyException() {
        super(ErrorCode.ADM_CASE_IS_EMPTY);
    }
}
