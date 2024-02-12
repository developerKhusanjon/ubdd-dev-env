package uz.ciasev.ubdd_service.service.dict.article;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import java.util.List;

public interface ArticleDTOService {

    ArticleResponseDTO convertToListDTO(Article articles);

    List<ArticleResponseDTO> convertToListDTO(List<Article> articles);

    ArticlePartListResponseDTO convertPartToListDTO(ArticlePart parts);

    List<ArticlePartListResponseDTO> convertPartToListDTO(List<ArticlePart> parts);

    Page<ArticlePartListResponseDTO> convertPartToListDTO(Page<ArticlePart> parts);
}
