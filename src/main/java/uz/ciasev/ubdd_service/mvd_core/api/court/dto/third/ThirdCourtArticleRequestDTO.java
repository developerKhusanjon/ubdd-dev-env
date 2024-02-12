package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import lombok.Data;

import java.util.List;

@Data
public class ThirdCourtArticleRequestDTO {

    private Long articleId;
    private Long articlePartId;
    private Long result;
    private List<ThirdCourtChangedToRequestDTO> changedTo;
}
