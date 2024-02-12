package uz.ciasev.ubdd_service.exception;

public class AdmCaseNotArrivedException extends ValidationException {

    public AdmCaseNotArrivedException() {
        super(ErrorCode.ADM_CASE_NOT_ARRIVED);
    }
}
