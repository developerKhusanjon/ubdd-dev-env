package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class PenaltyNotAllowedByArticlePartException extends ApplicationException {

    public PenaltyNotAllowedByArticlePartException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.PENALTY_NOT_ALLOWED_BY_ARTICLE_PART);
    }
}
