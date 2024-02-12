package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class PenaltyOnlyArticlePartException extends ApplicationException {

    public PenaltyOnlyArticlePartException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.PENALTY_ONLY_ARTICLE_PART);
    }
}
