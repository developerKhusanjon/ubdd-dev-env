package uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourtResolutionPdfRequestDTO {

    private Long fileId;
    private String username;
    private String password;
}
