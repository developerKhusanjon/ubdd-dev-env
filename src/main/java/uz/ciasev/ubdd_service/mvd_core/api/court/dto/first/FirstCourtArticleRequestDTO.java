package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FirstCourtArticleRequestDTO implements CourtArticleRequestDTO {

    private Long articleId;
    private Long articlePartId;
    private Long violationId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime violationTime;

}

