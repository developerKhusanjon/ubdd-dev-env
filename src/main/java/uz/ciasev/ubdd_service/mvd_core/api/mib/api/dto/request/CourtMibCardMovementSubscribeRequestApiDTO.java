package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;

@Data
public class CourtMibCardMovementSubscribeRequestApiDTO {

    @JsonProperty(value = "envelope_Id")
    private Long mibRequestId;

    public CourtMibCardMovementSubscribeRequestApiDTO(MibCardMovement cardMovement) {
        this.mibRequestId = cardMovement.getMibRequestId();
    }
}
