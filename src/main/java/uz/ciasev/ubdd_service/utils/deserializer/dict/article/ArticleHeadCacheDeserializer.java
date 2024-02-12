package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleHead;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleHeadCacheDeserializer extends AbstractDictDeserializer<ArticleHead> {

    @Autowired
    public ArticleHeadCacheDeserializer(DictionaryService<ArticleHead> articleHeadService) {
        super(ArticleHead.class, articleHeadService);
    }
}