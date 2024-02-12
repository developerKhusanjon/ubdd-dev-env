package uz.ciasev.ubdd_service.service.dict.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartViolationTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartViolationTypeUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartViolationTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartViolationTypeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticlePartViolationTypeServiceImpl implements ArticlePartViolationTypeService {

    private final ArticlePartViolationTypeRepository repository;

    @Override
    public List<ArticlePartViolationType> findByArticlePartId(Long id) {
        return repository.findAllByArticlePartId(id);
    }

//    @Override
//    public List<ArticlePartViolationType> findByArticleViolationTypeId(Long id) {
//        return repository.findAllByArticleViolationTypeId(id);
//    }

    @Override
    public Optional<ArticlePartViolationType> findByArticlePartIdAndViolationTypeId(ArticlePart part, ArticleViolationType violationType) {
        return repository.findByArticlePartIdAndArticleViolationTypeId(part.getId(), violationType.getId());
    }

    @Override
    public Optional<ArticlePartViolationType> findByArticlePartIdAndViolationTypeId(ProtocolArticle protocolArticle) {
        return repository.findByArticlePartIdAndArticleViolationTypeId(protocolArticle.getArticlePartId(), protocolArticle.getArticleViolationTypeId());
    }

//    @Override
//    public List<ArticleViolationType> findViolationTypesByArticlePartId(Long id) {
//        return repository.findAllByArticlePartId(id).stream()
//                .map(ArticlePartViolationType::getArticleViolationType)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ArticlePart> findArticlePartsByArticleViolationTypeId(Long id) {
//        return repository.findAllByArticleViolationTypeId(id).stream()
//                .map(ArticlePartViolationType::getArticlePart)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public ArticlePartViolationTypeResponseDTO create(ArticlePartViolationTypeCreateRequestDTO articlePartViolationTypeRequestDTO) {

        validateUniqueness(articlePartViolationTypeRequestDTO);
        validateIsDiscount(articlePartViolationTypeRequestDTO);

        ArticlePartViolationType savedType = save(articlePartViolationTypeRequestDTO.build());

        return new ArticlePartViolationTypeResponseDTO(savedType);
    }

    @Override
    public void update(Long id, ArticlePartViolationTypeUpdateRequestDTO requestDTO) {
        save(requestDTO.apply(getById(id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(getById(id));
    }

    @Override
    public Page<ArticlePartViolationTypeResponseDTO> findAllDTOByArticlePartId(Long id, Pageable pageable) {
        return repository.findAllByArticlePartId(id, pageable)
                .map(ArticlePartViolationTypeResponseDTO::new);
    }

    private void validateUniqueness(ArticlePartViolationTypeCreateRequestDTO articlePartViolationTypeRequestDTO) {
        Optional<ArticlePartViolationType> type = repository.findByArticlePartIdAndArticleViolationTypeId(
                articlePartViolationTypeRequestDTO.getArticlePart().getId(),
                articlePartViolationTypeRequestDTO.getArticleViolationType().getId()
        );
        if (type.isPresent()) {
            throw new ValidationException(ErrorCode.ARTICLE_PART_VIOLATION_TYPE_ALREADY_EXISTS);
        }
    }

    private void validateIsDiscount(ArticlePartViolationTypeCreateRequestDTO articlePartViolationTypeRequestDTO) {
        if (!articlePartViolationTypeRequestDTO.getArticlePart().isDiscount() && articlePartViolationTypeRequestDTO.getIsDiscount()) {
            throw new ValidationException(ErrorCode.DISCOUNT_FLAG_NOT_CONSIST_WITH_ARTICLE_PART);
        }
    }

    private ArticlePartViolationType save(ArticlePartViolationType articlePartViolationType) {
        return repository.save(articlePartViolationType);
    }

    @Override
    public ArticlePartViolationType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ArticlePartViolationType.class, id));
    }
}
