package uz.ciasev.ubdd_service.mvd_core.api.autocon.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AutoconDebtorResponseDTO extends AutoconResponseDTO {

    private String error;
}
