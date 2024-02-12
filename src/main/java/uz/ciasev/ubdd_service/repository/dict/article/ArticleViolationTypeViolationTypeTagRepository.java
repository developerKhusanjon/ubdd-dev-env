package uz.ciasev.ubdd_service.repository.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeViolationTypeTag;

public interface ArticleViolationTypeViolationTypeTagRepository extends JpaRepository<ArticleViolationTypeViolationTypeTag, Long> {

    boolean existsByArticleViolationTypeIdAndArticleViolationTypeTagId(Long articleViolationTypeId, Long articleViolationTypeTagId);

    Page<ArticleViolationTypeViolationTypeTag> findAllByArticleViolationTypeId(Long articleViolationTypeId, Pageable pageable);
}
