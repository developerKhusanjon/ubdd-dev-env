package uz.ciasev.ubdd_service.service.dict.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartArticleTagRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartArticleTag;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartArticleTagRepository;

@Service
@RequiredArgsConstructor
public class ArticlePartArticleTagServiceImpl implements ArticlePartArticleTagService {
    private final ArticlePartArticleTagRepository repository;

    @Override
    public ArticlePartArticleTag getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(ArticlePartArticleTag.class, id));
    }

    @Override
    @Transactional
    public ArticlePartArticleTag create(ArticlePartArticleTagRequestDTO requestDTO) {
        checkIfRelationAlreadyExists(requestDTO.getArticlePartId(), requestDTO.getArticleTagId());

        ArticlePartArticleTag apat = requestDTO.build();
        repository.save(apat);
        return apat;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ArticlePartArticleTag apat = getById(id);
        repository.delete(apat);
    }

    @Override
    public Page<ArticlePartArticleTag> findAllByArticlePartId(Long articlePartId, Pageable pageable) {
        return repository.findAllByArticlePartId(articlePartId, pageable);
    }

    private void checkIfRelationAlreadyExists(Long articlePartId, Long articleTagId) {
        if (repository.existsByArticlePartIdAndArticleTagId(articlePartId, articleTagId)) {
            throw new ValidationException(ErrorCode.THIS_TAG_FOR_THIS_ARTICLE_PART_ALREADY_EXISTS);
        }
    }
}
