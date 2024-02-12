package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class ArticleNumberAlreadyExistsException extends ValidationException {
    public ArticleNumberAlreadyExistsException(Integer number, Integer prim) {
        super(
                ErrorCode.ARTICLE_ALREADY_EXISTS,
                String.format(
                        "Article number %s prim %s already exists",
                        number,
                        prim
                )
        );
    }
}
