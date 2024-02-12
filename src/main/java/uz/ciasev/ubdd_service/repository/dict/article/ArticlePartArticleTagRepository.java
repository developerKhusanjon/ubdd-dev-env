package uz.ciasev.ubdd_service.repository.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartArticleTag;

public interface ArticlePartArticleTagRepository extends JpaRepository<ArticlePartArticleTag, Long> {

    boolean existsByArticlePartIdAndArticleTagId(Long articlePartId, Long articleTagId);

    Page<ArticlePartArticleTag> findAllByArticlePartId(Long articlePartId, Pageable pageable);
}
