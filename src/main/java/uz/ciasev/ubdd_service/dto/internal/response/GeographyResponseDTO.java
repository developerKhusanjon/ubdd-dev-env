package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DistrictResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.RegionResponseDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class GeographyResponseDTO {

    private List<RegionResponseDTO> regions;
    private List<DistrictResponseDTO> districts;

    public GeographyResponseDTO(List<RegionResponseDTO> regions, List<DistrictResponseDTO> districts) {
        this.regions = regions;
        this.districts = districts;
    }
}
