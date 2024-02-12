package uz.ciasev.ubdd_service.service.dict.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartViolationTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartViolationTypeUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartViolationTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;

import java.util.List;
import java.util.Optional;

public interface ArticlePartViolationTypeService {

    List<ArticlePartViolationType> findByArticlePartId(Long id);

    Optional<ArticlePartViolationType> findByArticlePartIdAndViolationTypeId(ArticlePart part, ArticleViolationType violationType);

    Optional<ArticlePartViolationType> findByArticlePartIdAndViolationTypeId(ProtocolArticle protocolArticle);

    ArticlePartViolationTypeResponseDTO create(ArticlePartViolationTypeCreateRequestDTO articlePartViolationTypeRequestDTO);

    void update(Long id, ArticlePartViolationTypeUpdateRequestDTO requestDTO);

    void delete(Long id);

    Page<ArticlePartViolationTypeResponseDTO> findAllDTOByArticlePartId(Long id, Pageable pageable);

    ArticlePartViolationType getById(Long id);
}
