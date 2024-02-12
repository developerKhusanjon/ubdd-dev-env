package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Mtp;

@Getter
public class MtpResponseDTO extends DictResponseDTO {
    private final Long district;

    public MtpResponseDTO(Mtp mtp) {
        super(mtp);
        this.district = mtp.getDistrictId();
    }
}
