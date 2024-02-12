package uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FiveCourtPerformancesRequestDTO {

    private Long defendantId;

    private Long violatorId;

    private Long mibEnvelopeId;

    private Long type;

    @JsonProperty("regionId")
    private Long externalDistrictId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime sendTime;

    private List<Long> evidenceIds;
}
