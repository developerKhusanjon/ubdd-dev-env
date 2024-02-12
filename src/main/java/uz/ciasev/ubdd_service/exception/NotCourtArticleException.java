package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class NotCourtArticleException extends ApplicationException {

    public NotCourtArticleException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NOT_COURT_ARTICLE);
    }
}
