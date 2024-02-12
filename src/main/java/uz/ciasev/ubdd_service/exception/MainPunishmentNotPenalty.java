package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class MainPunishmentNotPenalty  extends ApplicationException {

    public MainPunishmentNotPenalty() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.MAIN_PUNISHMENT_NOT_PENALTY);
    }
}

