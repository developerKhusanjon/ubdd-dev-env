package uz.ciasev.ubdd_service.repository.dict.article;

import org.springframework.data.domain.Sort;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.List;

public interface ArticlePartRepository extends AbstractDictRepository<ArticlePart> {

    List<ArticlePart> findAllByIsActiveTrueAndArticleId(Long articleId, Sort sort);
}
