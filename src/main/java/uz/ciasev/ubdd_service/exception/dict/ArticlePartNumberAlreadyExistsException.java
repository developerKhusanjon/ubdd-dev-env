package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class ArticlePartNumberAlreadyExistsException extends ValidationException {
    public ArticlePartNumberAlreadyExistsException(Integer number, Integer prim, Integer part) {
        super(
                ErrorCode.ARTICLE_PART_ALREADY_EXISTS,
                String.format(
                        "Article number %s prim %s part %s already exists",
                        number,
                        prim,
                        part
                )
        );
    }
}
