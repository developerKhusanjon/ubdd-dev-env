package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleCacheDeserializer extends AbstractDictDeserializer<Article> {

    @Autowired
    public ArticleCacheDeserializer(DictionaryService<Article> articleService) {
        super(Article.class, articleService);
    }
}