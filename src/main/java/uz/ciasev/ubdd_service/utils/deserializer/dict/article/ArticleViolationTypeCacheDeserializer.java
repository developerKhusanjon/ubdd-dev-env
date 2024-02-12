package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleViolationTypeCacheDeserializer extends AbstractDictDeserializer<ArticleViolationType> {

    @Autowired
    public ArticleViolationTypeCacheDeserializer(DictionaryService<ArticleViolationType> articleViolationTypeService) {
        super(ArticleViolationType.class, articleViolationTypeService);
    }
}