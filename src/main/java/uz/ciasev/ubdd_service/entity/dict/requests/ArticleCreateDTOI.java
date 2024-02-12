package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.article.ArticleHead;

public interface ArticleCreateDTOI extends DictCreateDTOI, ArticleUpdateDTOI {
    ArticleHead getArticleHead();
}
