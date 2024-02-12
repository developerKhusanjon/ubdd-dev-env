package uz.ciasev.ubdd_service.exception;


public class ResolutionNotActiveException extends ForbiddenException {

    public ResolutionNotActiveException() {
        super(ErrorCode.RESOLUTION_NOT_ACTIVE, "This resolution was canceled");
    }
}
