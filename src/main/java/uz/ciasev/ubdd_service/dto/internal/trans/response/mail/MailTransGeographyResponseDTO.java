package uz.ciasev.ubdd_service.dto.internal.trans.response.mail;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;

@Getter
public class MailTransGeographyResponseDTO {

    private final Long id;
    private final Long externalRegionId;
    private final Long externalDistrictId;
    private final Long regionId;
    private final Long districtId;

    public MailTransGeographyResponseDTO(MailTransGeography courtTransfer) {
        this.id = courtTransfer.getId();
        this.externalRegionId = courtTransfer.getExternalRegionId();
        this.externalDistrictId = courtTransfer.getExternalDistrictId();
        this.regionId = courtTransfer.getRegionId();
        this.districtId = courtTransfer.getDistrictId();
    }
}
