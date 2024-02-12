package uz.ciasev.ubdd_service.dto.internal.trans.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

@Getter
public class CourtTransferResponseDTO {

    private final Long id;
    private final Long externalId;
    private final Long regionId;
    private final Long districtId;

    public CourtTransferResponseDTO(CourtTransfer courtTransfer) {
        this.id = courtTransfer.getId();
        this.externalId = courtTransfer.getExternalId();
        this.regionId = courtTransfer.getRegionId();
        this.districtId = courtTransfer.getDistrictId();
    }
}
