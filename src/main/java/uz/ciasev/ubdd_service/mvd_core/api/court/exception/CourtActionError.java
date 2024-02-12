package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class CourtActionError extends CourtValidationException {

    public CourtActionError(String message) {
        super(message);
    }
}
