package uz.ciasev.ubdd_service.repository.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;

import java.util.List;
import java.util.Optional;

public interface ArticlePartViolationTypeRepository extends JpaRepository<ArticlePartViolationType, Long> {

    List<ArticlePartViolationType> findAllByArticlePartId(Long id);
    Page<ArticlePartViolationType> findAllByArticlePartId(Long id, Pageable pageable);
    List<ArticlePartViolationType> findAllByArticleViolationTypeId(Long id);
    Optional<ArticlePartViolationType> findByArticlePartIdAndArticleViolationTypeId(Long partId, Long violationTypeId);

    @Query("SELECT apvt " +
            "FROM ArticlePartViolationType apvt " +
            "WHERE apvt.articlePartId IN :articlePartsId " +
            "AND apvt.articlePart.isActive = TRUE " +
            "AND apvt.articleViolationType.isActive = TRUE ")
    List<ArticlePartViolationType> findAllActiveByArticlePartsId(Iterable<Long> articlePartsId);
}
