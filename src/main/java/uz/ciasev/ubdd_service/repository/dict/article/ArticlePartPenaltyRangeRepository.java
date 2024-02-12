package uz.ciasev.ubdd_service.repository.dict.article;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartPenaltyRange;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArticlePartPenaltyRangeRepository extends JpaRepository<ArticlePartPenaltyRange, Long> {

    void deleteAllByArticlePartId(Long id);
    List<ArticlePartPenaltyRange> findAllByArticlePartId(Long id);
    Optional<ArticlePartPenaltyRange> findTop1ByArticlePartIdAndFromDateLessThanEqualOrderByFromDateDesc(Long articlePartId, LocalDate date);
}
