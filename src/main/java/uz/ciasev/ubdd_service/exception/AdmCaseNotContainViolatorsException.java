package uz.ciasev.ubdd_service.exception;

public class AdmCaseNotContainViolatorsException extends ValidationException {
    public AdmCaseNotContainViolatorsException() {
        super(ErrorCode.NOT_FOUND_VIOLATORS_IN_ADM_CASE);
    }
}
