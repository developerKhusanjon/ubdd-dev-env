package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartHardLevel;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartHardLevelRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArticlePartHardLevelDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<ArticlePartHardLevel>
        implements ArticlePartHardLevelDictionaryService {

    private final String subPath = "article-part-hard-levels";

    private final ArticlePartHardLevelRepository repository;
    private final Class<ArticlePartHardLevel> entityClass = ArticlePartHardLevel.class;
}
