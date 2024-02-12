package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class ViolatorMainArticlePartNotFoundException extends ApplicationException {

    public ViolatorMainArticlePartNotFoundException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.VIOLATOR_MAIN_ARTICLE_PART_NOT_FOUND);
    }
}
