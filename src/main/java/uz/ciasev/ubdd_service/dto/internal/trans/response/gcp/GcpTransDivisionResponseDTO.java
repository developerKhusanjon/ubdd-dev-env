package uz.ciasev.ubdd_service.dto.internal.trans.response.gcp;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransDivision;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class GcpTransDivisionResponseDTO extends AbstractGcpTransEntityResponseDTO {

    private final MultiLanguage name;
    private final String address;
    private final Long countryId;
    private final Long regionId;
    private final Long districtId;

    public GcpTransDivisionResponseDTO(GcpTransDivision entity) {
        super(entity);
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.countryId = entity.getCountryId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
    }
}
