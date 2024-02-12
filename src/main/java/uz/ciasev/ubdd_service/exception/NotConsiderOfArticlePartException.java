package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class NotConsiderOfArticlePartException extends ApplicationException {

    public NotConsiderOfArticlePartException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NOT_CONSIDER_OF_ARTICLE_PART);
    }
}
