package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import lombok.Data;

@Data
public class FirstCourtEarlyArticleRequestDTO implements CourtArticleRequestDTO {

    private Long articleId;
    private Long articlePartId;
    private Long violationId;

}

