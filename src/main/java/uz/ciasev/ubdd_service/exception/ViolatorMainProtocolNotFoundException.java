package uz.ciasev.ubdd_service.exception;

public class ViolatorMainProtocolNotFoundException extends ValidationException {
    public ViolatorMainProtocolNotFoundException() {
        super(ErrorCode.VIOLATOR_MAIN_PROTOCOL_NOT_FOUND);
    }
}
