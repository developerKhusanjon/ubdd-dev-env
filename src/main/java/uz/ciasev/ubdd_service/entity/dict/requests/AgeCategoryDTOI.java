package uz.ciasev.ubdd_service.entity.dict.requests;

public interface AgeCategoryDTOI extends DictCreateDTOI, DictUpdateDTOI {
    Integer getAgeFrom();
    Integer getAgeTo();
    Boolean getIsJuvenile();
    Boolean getIsViolatorOnly();
}
