package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleHead;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleHeadRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleHeadDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<ArticleHead>
        implements ArticleHeadDictionaryService {

    private final String subPath = "article-heads";

    private final ArticleHeadRepository repository;
    private final Class<ArticleHead> entityClass = ArticleHead.class;
}
