package uz.ciasev.ubdd_service.entity.dict.requests.trans;

import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

public interface CourtTransArticleCreateDTOI {

    ArticlePart getArticlePart();
    Long getExternalArticleId();
    Long getExternalArticlePartId();
}
