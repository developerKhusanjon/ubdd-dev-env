package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticlePartListResponseDTO extends ArticlePartResponseDTO {

    private List<Long> violationTypes;

    public ArticlePartListResponseDTO(ArticlePart articlePart, List<ArticlePartViolationType> types) {
        super(articlePart);
        this.violationTypes = types.stream()
                .map(ArticlePartViolationType::getArticleViolationTypeId)
                .collect(Collectors.toList());
    }
}
