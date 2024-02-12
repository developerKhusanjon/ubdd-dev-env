package uz.ciasev.ubdd_service.service.dict.article;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.citizenapi.article.ArticleViolationTypeResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleViolationTypeRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleViolationTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleViolationTypeDictionaryServiceImpl extends AbstractDictionaryCRUDService<ArticleViolationType, ArticleViolationTypeRequestDTO, ArticleViolationTypeRequestDTO>
        implements ArticleViolationTypeDictionaryService {

    private final String subPath = "article-violation-types";
    private final TypeReference<ArticleViolationTypeRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ArticleViolationTypeRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final ArticleViolationTypeRepository repository;
    private final Class<ArticleViolationType> entityClass = ArticleViolationType.class;

    public ArticleViolationTypeResponseDTO buildResponseDTO(ArticleViolationType articleViolationType) {
        return new ArticleViolationTypeResponseDTO(articleViolationType);
    }

}
