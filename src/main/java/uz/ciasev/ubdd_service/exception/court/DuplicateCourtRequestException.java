package uz.ciasev.ubdd_service.exception.court;

public class DuplicateCourtRequestException extends ExternalException {

    public DuplicateCourtRequestException(int requestHashCode) {
        super(CourtResult.SUCCESSFULLY, String.format("Request with hash %s already successfully processed!", requestHashCode));
    }
}
