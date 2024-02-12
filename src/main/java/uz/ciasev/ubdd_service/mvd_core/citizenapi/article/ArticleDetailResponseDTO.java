package uz.ciasev.ubdd_service.mvd_core.citizenapi.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.article.Article;

import java.util.List;

@Getter
public class ArticleDetailResponseDTO extends ArticleResponseDTO {

    List<ArticlePartDetailResponseDTO> parts;

    public ArticleDetailResponseDTO(Article articlePart, List<ArticlePartDetailResponseDTO> parts) {
        super(articlePart);
        this.parts = parts;
    }
}
