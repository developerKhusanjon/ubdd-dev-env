package uz.ciasev.ubdd_service.service.dict.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticleViolationTypeViolationTypeTagRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeViolationTypeTag;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleViolationTypeViolationTypeTagRepository;

@Service
@RequiredArgsConstructor
public class ArticleViolationTypeViolationTypeTagServiceImpl implements ArticleViolationTypeViolationTypeTagService {
    private final ArticleViolationTypeViolationTypeTagRepository repository;

    @Override
    public ArticleViolationTypeViolationTypeTag getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(ArticleViolationTypeViolationTypeTag.class, id));
    }

    @Override
    @Transactional
    public ArticleViolationTypeViolationTypeTag create(ArticleViolationTypeViolationTypeTagRequestDTO requestDTO) {
        checkIfRelationAlreadyExists(requestDTO.getArticleViolationTypeId(), requestDTO.getArticleViolationTypeTagId());

        ArticleViolationTypeViolationTypeTag typeTag = requestDTO.build();
        repository.save(typeTag);
        return typeTag;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ArticleViolationTypeViolationTypeTag typeTag = getById(id);
        repository.delete(typeTag);
    }

    @Override
    public Page<ArticleViolationTypeViolationTypeTag> findAllByViolationTypeId(Long articleViolationTypeId, Pageable pageable) {
        return repository.findAllByArticleViolationTypeId(articleViolationTypeId, pageable);
    }

    @Override
    public Page<ArticleViolationTypeViolationTypeTag> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private void checkIfRelationAlreadyExists(Long articlePartId, Long articleTagId) {
        if (repository.existsByArticleViolationTypeIdAndArticleViolationTypeTagId(articlePartId, articleTagId)) {
            throw new ValidationException(ErrorCode.THIS_TAG_FOR_THIS_VIOLATION_TYPE_ALREADY_EXISTS);
        }
    }
}
