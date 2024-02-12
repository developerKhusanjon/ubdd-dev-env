package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartHardLevel;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticlePartHardLevelCacheDeserializer extends AbstractDictDeserializer<ArticlePartHardLevel> {

    @Autowired
    public ArticlePartHardLevelCacheDeserializer(DictionaryService<ArticlePartHardLevel> service) {
        super(ArticlePartHardLevel.class, service);
    }
}