package uz.ciasev.ubdd_service.service.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartArticleTagRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartArticleTag;

public interface ArticlePartArticleTagService {

    ArticlePartArticleTag getById(Long id);

    ArticlePartArticleTag create(ArticlePartArticleTagRequestDTO requestDTO);

    void delete(Long id);

    Page<ArticlePartArticleTag> findAllByArticlePartId(Long articlePartId, Pageable pageable);

}
