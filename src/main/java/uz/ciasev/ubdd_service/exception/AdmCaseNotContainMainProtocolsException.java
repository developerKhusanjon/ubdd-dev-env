package uz.ciasev.ubdd_service.exception;

public class AdmCaseNotContainMainProtocolsException extends ValidationException {
    public AdmCaseNotContainMainProtocolsException() {
        super(ErrorCode.NOT_FOUND_MAIN_PROTOCOLS_IN_ADM_CASE);
    }
}
