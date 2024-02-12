package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

@Data
public class DashboardFilterDTO {

    private final Long organId;
    private final Long regionId;
    private final Long districtId;

    public DashboardFilterDTO(Long organId, Long regionId, Long districtId) {
        this.organId = organId;
        this.regionId = regionId;
        this.districtId = districtId;
    }

    public static DashboardFilterDTO of(Long organId, Long regionId, Long districtId) {
        return new DashboardFilterDTO(organId, regionId, districtId);
    }
}
