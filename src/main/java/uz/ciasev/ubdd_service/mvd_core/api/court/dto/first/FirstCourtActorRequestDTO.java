package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

public interface FirstCourtActorRequestDTO extends FirstCourtPersonRequestDTO {

    void setBirthRegion(Long value);
    void setBirthDistrict(Long value);
    void setBirthCountry(Long value);
}