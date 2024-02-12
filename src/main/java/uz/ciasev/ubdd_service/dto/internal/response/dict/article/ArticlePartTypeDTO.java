package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArticlePartTypeDTO {

    private Long articleId;
    private List<ArticlePartResponseDTO> articleParts;
    private List<ArticleViolationTypeResponseDTO> articleViolationTypes;
}
