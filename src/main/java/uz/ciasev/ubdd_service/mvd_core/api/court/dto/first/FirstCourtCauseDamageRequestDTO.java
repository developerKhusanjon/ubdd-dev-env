package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FirstCourtCauseDamageRequestDTO {

    private Long inFavorType;
    private List<FirstCourtFavorListRequestDTO> inWhoseFavorList;
}
