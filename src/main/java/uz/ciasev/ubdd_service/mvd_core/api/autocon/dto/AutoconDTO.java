package uz.ciasev.ubdd_service.mvd_core.api.autocon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;

@Getter
public class AutoconDTO {

    @JsonProperty("pUID")
    private final Long id;

    public AutoconDTO(AutoconSending sending) {
        this.id = sending.getId();
    }
}
