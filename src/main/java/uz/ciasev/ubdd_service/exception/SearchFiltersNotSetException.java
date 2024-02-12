package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class SearchFiltersNotSetException extends ApplicationException {

    public SearchFiltersNotSetException() {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.FILTERS_NOT_SET
        );
    }
}
