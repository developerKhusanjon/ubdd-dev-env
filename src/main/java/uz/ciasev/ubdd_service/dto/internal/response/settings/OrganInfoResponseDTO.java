package uz.ciasev.ubdd_service.dto.internal.response.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;

@Getter
public class OrganInfoResponseDTO {
    private final Long id;
    private final Boolean isActive;
    private final Long organId;
    private final Long regionId;
    private final Long districtId;
    private final Long departmentId;
    private final String address;
    private final String landline;
    private final String postIndex;

    public OrganInfoResponseDTO(OrganInfo entity) {
        this.id = entity.getId();
        this.isActive = entity.isActive();
        this.organId = entity.getOrganId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
        this.departmentId = entity.getDepartmentId();
        this.address = entity.getAddress();
        this.landline = entity.getLandline();
        this.postIndex = entity.getPostIndex();
    }
}
