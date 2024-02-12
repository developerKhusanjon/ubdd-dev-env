package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ArticleViolationResponseDTO {

    private ArticlePartResponseDTO recommendedArticlePart;
    private List<ViolationResponseDTO> protocols;

    public ArticleViolationResponseDTO(List<ViolationResponseDTO> protocols,
                                       ArticlePart recommendedArticlePart) {
        this.recommendedArticlePart = Optional.ofNullable(recommendedArticlePart)
                .map(ArticlePartResponseDTO::new)
                .orElse(null);
        this.protocols = protocols;
    }

    public ArticleViolationResponseDTO(List<ViolationResponseDTO> protocols) {
        this(protocols, null);
    }

    public ArticleViolationResponseDTO() {
        this(Collections.emptyList(), null);
    }
}
