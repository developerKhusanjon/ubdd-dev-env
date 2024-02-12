package uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

import java.time.LocalDateTime;

@Data
public class ViolationEventApiDTO {

    private Long id;
    private LocalDateTime createdTime;
    private Long type;
    private String vehicleNumber;
    private String vehicleNumberPhotoPath;
    private String nearViewPhotoPath;
    private String farViewPhotoPath;

    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @JsonProperty(value = "violationTypeId")
    private ArticleViolationType violationType;

    private LocalDateTime violationTime;

    @JsonProperty(value = "regionId")
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    private String address;
    private Double latitude;
    private Double longitude;

    private RadarViolationEventApiDTO radarEvent;
}
