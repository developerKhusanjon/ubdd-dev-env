package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutor;

import java.time.LocalDateTime;

@Data
public abstract class AbstractProsecutorResponseDTO {
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long regionId;
    private final Long districtId;
    private final String prosecutorInfo;
    private final Long rankId;
    private final Long positionId;
    private final String description;

    public AbstractProsecutorResponseDTO(AbstractProsecutor prosecutor) {
        this.createdTime = prosecutor.getCreatedTime();
        this.editedTime = prosecutor.getEditedTime();
        this.regionId = prosecutor.getRegionId();
        this.districtId = prosecutor.getDistrictId();
        this.prosecutorInfo = prosecutor.getProsecutorInfo();
        this.rankId = prosecutor.getRankId();
        this.positionId = prosecutor.getPositionId();
        this.description = prosecutor.getDescription();
    }
}
