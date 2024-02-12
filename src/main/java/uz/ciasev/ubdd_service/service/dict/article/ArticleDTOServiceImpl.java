package uz.ciasev.ubdd_service.service.dict.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleDTOServiceImpl implements ArticleDTOService {
    private final ArticlePartViolationTypeService articlePartViolationTypeService;


    @Override
    public ArticleResponseDTO convertToListDTO(Article articles) {
        return new ArticleResponseDTO(articles);
    }
    
    @Override
    public List<ArticleResponseDTO> convertToListDTO(List<Article> articles) {
        return articles.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArticlePartListResponseDTO convertPartToListDTO(ArticlePart parts) {
        return new ArticlePartListResponseDTO(parts, articlePartViolationTypeService.findByArticlePartId(parts.getId()));
    }

    @Override
    public List<ArticlePartListResponseDTO> convertPartToListDTO(List<ArticlePart> parts) {
//        List<ArticleViolationType> violationTypes = articlePartViolationTypeService.findByArticlePartsId(parts.stream().map(ArticlePart::getId));
//        violationTypes.stream().collect(Collectors.groupingBy(violationType -> violationType.))

        return parts.stream()
                .map(this::convertPartToListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ArticlePartListResponseDTO> convertPartToListDTO(Page<ArticlePart> parts) {
        return parts
                .map(this::convertPartToListDTO);
    }
}
