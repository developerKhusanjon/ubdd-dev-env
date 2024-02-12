package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;

@Data
public class ProtocolLocationResponseDTO {

    private double latitude;
    private double longitude;

    public ProtocolLocationResponseDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
