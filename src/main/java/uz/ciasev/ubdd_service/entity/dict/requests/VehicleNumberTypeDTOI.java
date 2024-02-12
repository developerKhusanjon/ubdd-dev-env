package uz.ciasev.ubdd_service.entity.dict.requests;

public interface VehicleNumberTypeDTOI extends DictUpdateDTOI {
    Boolean getIsNeedSendToMail();
    Boolean getIsNeedSendToMID();
    Boolean getIsNeedMoveToVAI();
}
