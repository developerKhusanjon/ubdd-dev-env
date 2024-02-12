package uz.ciasev.ubdd_service.entity.dict.requests;

public interface RegionDTOI extends DictCreateDTOI, DictUpdateDTOI {
    Boolean getIsState();
    String getSerialName();
}
