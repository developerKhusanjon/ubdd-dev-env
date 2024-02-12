package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticlePartCacheDeserializer extends AbstractDictDeserializer<ArticlePart> {

    @Autowired
    public ArticlePartCacheDeserializer(ArticlePartDictionaryService articlePartService) {
        super(ArticlePart.class, articlePartService);
    }
}