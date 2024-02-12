package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleParticipantTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleParticipantTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<ArticleParticipantType, ArticleParticipantTypeAlias>
        implements ArticleParticipantTypeDictionaryService {

    private final String subPath = "article-participant-types";

    private final ArticleParticipantTypeRepository repository;
    private final Class<ArticleParticipantType> entityClass = ArticleParticipantType.class;

    @Override
    public Class<ArticleParticipantTypeAlias> getAliasClass() {
        return ArticleParticipantTypeAlias.class;
    }
}
