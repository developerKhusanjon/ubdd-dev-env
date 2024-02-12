package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleTag;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleTagRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleTagDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<ArticleTag>
        implements ArticleTagDictionaryService {
    private final String subPath = "article-tags";

    private final ArticleTagRepository repository;
    private final Class<ArticleTag> entityClass = ArticleTag.class;
}
