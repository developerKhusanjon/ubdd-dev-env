package uz.ciasev.ubdd_service.mvd_core.api.autocon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoconResponseDTO {
    private Integer status;
    private String message;
}
