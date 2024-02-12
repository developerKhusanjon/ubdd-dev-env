package uz.ciasev.ubdd_service.entity.dict.requests;

public interface UserStatusDTOI extends DictCreateDTOI, DictUpdateDTOI {
    Boolean getIsUserActive();
    String getColor();
}
