package uz.ciasev.ubdd_service.utils.deserializer.dict.article;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArticleParticipantTypeCacheDeserializer extends AbstractDictDeserializer<ArticleParticipantType> {

    @Autowired
    public ArticleParticipantTypeCacheDeserializer(DictionaryService<ArticleParticipantType> articleParticipantTypeService) {
        super(ArticleParticipantType.class, articleParticipantTypeService);
    }
}