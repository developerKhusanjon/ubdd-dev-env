package uz.ciasev.ubdd_service.dto.internal.trans.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;

@Getter
public class CourtTransArticleResponseDTO {

    private final Long id;
    private final Long articlePartId;
    private final Long externalArticleId;
    private final Long externalArticlePartId;

    public CourtTransArticleResponseDTO(CourtTransArticle entity) {
        this.id = entity.getId();
        this.articlePartId = entity.getArticlePartId();
        this.externalArticleId = entity.getExternalArticleId();
        this.externalArticlePartId = entity.getExternalArticlePartId();
    }
}
