package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class PunishmentAlreadyExecutedException extends ApplicationException {

    public PunishmentAlreadyExecutedException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.PUNISHMENT_ALREADY_EXECUTED);
    }
}
