package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleTag;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleTagCacheDeserializer extends AbstractDictDeserializer<ArticleTag> {

    @Autowired
    public ArticleTagCacheDeserializer(DictionaryService<ArticleTag> service) {
        super(ArticleTag.class, service);
    }
}
