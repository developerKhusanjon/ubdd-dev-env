package uz.ciasev.ubdd_service.repository.court.trans;

import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;
import uz.ciasev.ubdd_service.repository.trans.AbstractTransEntityRepository;

import java.util.Optional;

public interface CourtTransArticleRepository extends AbstractTransEntityRepository<CourtTransArticle> {

    @Query("SELECT ca " +
            " FROM CourtTransArticle ca " +
            "WHERE ca.articlePartId = :articlePart")
    Optional<CourtTransArticle> findByInternalArticleAndPart(Long article, Long articlePart);

    boolean existsByArticlePart(ArticlePart articlePart);
}
