package uz.ciasev.ubdd_service.dto.internal.trans.response.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;

@Getter
public class MibTransGeographyResponseDTO {

    private final Long id;
    private final Long externalId;
    private final Boolean isAvailableForSendMibExecutionCard;
    private final Boolean isAvailableForMibProtocolRegistration;
    private final Long regionId;
    private final Long districtId;

    public MibTransGeographyResponseDTO(MibTransGeography entity) {
        this.id = entity.getId();
        this.externalId = entity.getExternalId();
        this.isAvailableForSendMibExecutionCard = entity.getIsAvailableForSendMibExecutionCard();
        this.isAvailableForMibProtocolRegistration = entity.getIsAvailableForMibProtocolRegistration();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
    }
}
