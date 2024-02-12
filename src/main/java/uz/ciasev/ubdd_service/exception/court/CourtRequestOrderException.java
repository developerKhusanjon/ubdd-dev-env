package uz.ciasev.ubdd_service.exception.court;

import java.util.List;

public class CourtRequestOrderException extends ExternalException {

    public CourtRequestOrderException(List<Long> claims) {
        super(CourtResult.VALIDATION_ERROR, String.format("Request mast not be processed while claims %s not processed", claims.toString()));
    }
}
