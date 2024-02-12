package uz.ciasev.ubdd_service.service.trans.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransArticleResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransArticleCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransArticleRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class CourtTransArticleAdminServiceImpl extends AbstractTransEntityCRDService<CourtTransArticle, CourtTransArticleCreateRequestDTO>
        implements CourtTransArticleAdminService {

    private final CourtTransArticleRepository repository;
    private final String subPath = "court/article";
    private final Class<CourtTransArticle> entityClass = CourtTransArticle.class;

    private final TypeReference<CourtTransArticleCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public CourtTransArticle create(CourtTransArticleCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(CourtTransArticle entity) {
        return new CourtTransArticleResponseDTO(entity);
    }

    private void validate(CourtTransArticleCreateRequestDTO requestDTO) {
        ArticlePart articlePart = requestDTO.getArticlePart();

        if (repository.existsByArticlePart(articlePart)) {
            throw new EntityByParamsAlreadyExists(entityClass, "articlePartId", articlePart.getId());
        }
    }
}
