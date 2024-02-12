package uz.ciasev.ubdd_service.exception.court;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.ApplicationException;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CourtWrappedException extends ExternalException {

    private final ApplicationException applicationException;

    public CourtWrappedException(ApplicationException e, Long envelopeId) {
        super(e.getCode() + ": " + e.getDetail());
        this.applicationException = e;
        this.setEnvelopeId(envelopeId);
    }
}
