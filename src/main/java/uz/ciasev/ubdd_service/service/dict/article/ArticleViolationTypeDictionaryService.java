package uz.ciasev.ubdd_service.service.dict.article;

import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleViolationTypeRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;


public interface ArticleViolationTypeDictionaryService extends DictionaryCRUDService<ArticleViolationType, ArticleViolationTypeRequestDTO, ArticleViolationTypeRequestDTO> {
}
