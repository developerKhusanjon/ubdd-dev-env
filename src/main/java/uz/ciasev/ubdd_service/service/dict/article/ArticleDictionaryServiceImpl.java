package uz.ciasev.ubdd_service.service.dict.article;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ArticleUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class ArticleDictionaryServiceImpl extends AbstractDictionaryCRUDService<Article, ArticleCreateRequestDTO, ArticleUpdateRequestDTO>
        implements ArticleDictionaryService {

    private final ArticlePartDictionaryService partDictionaryService;

    private final ArticleDTOService dtoService;
    private final String subPath = "articles";
    private final TypeReference<ArticleCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ArticleUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final ArticleRepository repository;
    private final Class<Article> entityClass = Article.class;

    @Override
    public ArticleResponseDTO buildResponseDTO(Article entity) {
        return dtoService.convertToListDTO(entity);
    }

    @Override
    public ArticleResponseDTO buildListResponseDTO(Article entity) {return dtoService.convertToListDTO(entity);}

    @Override
    public Article update(Long id, ArticleUpdateRequestDTO request) {
        Article editedArticle = super.update(id, request);
        partDictionaryService.articleUpdateCallback(editedArticle);
        return editedArticle;
    }
}
