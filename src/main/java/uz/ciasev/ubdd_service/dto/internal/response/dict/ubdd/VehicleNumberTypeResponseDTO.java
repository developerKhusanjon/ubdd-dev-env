package uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;

@Getter
public class VehicleNumberTypeResponseDTO extends DictResponseDTO {
    private final Boolean isNeedSendToMail;
    private final Boolean isNeedSendToMID;
    private final Boolean isNeedMoveToVAI;

    public VehicleNumberTypeResponseDTO(VehicleNumberType entity) {
        super(entity);
        this.isNeedSendToMail = entity.getIsNeedSendToMail();
        this.isNeedSendToMID = entity.getIsNeedSendToMID();
        this.isNeedMoveToVAI = entity.getIsNeedMoveToVAI();
    }
}
