package uz.ciasev.ubdd_service.service.dict.article;

import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface ArticleDictionaryService extends DictionaryCRUDService<Article, ArticleCreateRequestDTO, ArticleUpdateRequestDTO> {
}
