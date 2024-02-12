package uz.ciasev.ubdd_service.exception.court;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalException extends RuntimeException {

    private Long envelopeId;
    private CourtResult result;
    private String description;

    public ExternalException(CourtResult result, String message) {
        this.result = result;
        this.description = message;
        this.envelopeId = null;
    }

    public ExternalException(String message) {
        this(CourtResult.VALIDATION_ERROR, message);
    }
}
