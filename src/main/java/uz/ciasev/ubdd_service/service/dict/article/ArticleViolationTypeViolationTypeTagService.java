package uz.ciasev.ubdd_service.service.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticleViolationTypeViolationTypeTagRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeViolationTypeTag;

public interface ArticleViolationTypeViolationTypeTagService {

    ArticleViolationTypeViolationTypeTag getById(Long id);

    ArticleViolationTypeViolationTypeTag create(ArticleViolationTypeViolationTypeTagRequestDTO requestDTO);

    void delete(Long id);

    Page<ArticleViolationTypeViolationTypeTag> findAllByViolationTypeId(Long violationTypeId, Pageable pageable);

    Page<ArticleViolationTypeViolationTypeTag> findAll(Pageable pageable);
}
