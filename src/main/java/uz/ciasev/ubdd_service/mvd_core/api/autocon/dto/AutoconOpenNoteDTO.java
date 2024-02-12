package uz.ciasev.ubdd_service.mvd_core.api.autocon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AutoconOpenNoteDTO {

    private String owner;

    private String model;

    @JsonProperty("datetime")
    private LocalDateTime dateTime;

    @JsonProperty("resolution")
    private String decisionSeriesNumber;

    private String amount;

    @JsonProperty("reason")
    private String articleName;

    @JsonProperty("region_id")
    private Long regionId;
}
