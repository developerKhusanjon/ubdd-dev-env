package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.District;

@Getter
public class DistrictResponseDTO extends DistrictListResponseDTO {
    private final String reportName;
    private final Boolean isNotDistrict;
    private final Boolean isState;

    public DistrictResponseDTO(District entity) {
        super(entity);
        this.reportName = entity.getReportName();
        this.isNotDistrict = entity.getIsNotDistrict();
        this.isState = entity.getIsState();
    }
}
