package uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto;

import lombok.Data;

@Data
public class RadarViolationEventApiDTO {
    private Double maxAllowedSpeed;
    private Double fixedSpeed;

    public RadarViolationEventApiDTO(Double maxAllowedSpeed, Double fixedSpeed){
        this.maxAllowedSpeed = maxAllowedSpeed;
        this.fixedSpeed = fixedSpeed;
    }
}
