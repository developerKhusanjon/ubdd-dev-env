package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeTag;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleViolationTypeTagCacheDeserializer extends AbstractDictDeserializer<ArticleViolationTypeTag> {

    @Autowired
    public ArticleViolationTypeTagCacheDeserializer(DictionaryService<ArticleViolationTypeTag> service) {
        super(ArticleViolationTypeTag.class, service);
    }
}
