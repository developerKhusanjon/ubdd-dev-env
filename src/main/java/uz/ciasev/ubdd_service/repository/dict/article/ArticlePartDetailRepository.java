package uz.ciasev.ubdd_service.repository.dict.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartDetail;

import java.util.List;
import java.util.Optional;

public interface ArticlePartDetailRepository extends JpaRepository<ArticlePartDetail, Long> {

    Optional<ArticlePartDetail> findByArticlePartId(Long id);

    @Query("SELECT d FROM ArticlePartDetail d WHERE d.articlePart.articleId = :articleId")
    List<ArticlePartDetail> findByArticleId(Long articleId);
}
