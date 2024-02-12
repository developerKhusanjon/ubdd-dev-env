package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeTag;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeTagAlias;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleViolationTypeTagRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleViolationTypeTagDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<ArticleViolationTypeTag, ArticleViolationTypeTagAlias>
        implements ArticleViolationTypeTagDictionaryService {

    private final String subPath = "article-violation-type-tags";

    private final ArticleViolationTypeTagRepository repository;
    private final Class<ArticleViolationTypeTag> entityClass = ArticleViolationTypeTag.class;

    @Override
    public Class<ArticleViolationTypeTagAlias> getAliasClass() {
        return ArticleViolationTypeTagAlias.class;
    }
}
