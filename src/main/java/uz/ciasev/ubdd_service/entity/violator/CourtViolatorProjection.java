package uz.ciasev.ubdd_service.entity.violator;

import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;

import java.time.LocalDate;

public interface CourtViolatorProjection {

    Long getId();
    Long getPersonId();
    String getMobile();
    Long getHealthStatusId();
    Long getEducationLevelId();
    Long getViolationRepeatabilityStatusId();
    Long getMaritalStatusId();
    Long getChildrenAmount();
    String getPinpp();
    Boolean getIsRealPinpp();
    String getLastNameLat();
    String getFirstNameLat();
    String getSecondNameLat();
    String getLastNameKir();
    String getFirstNameKir();
    String getSecondNameKir();
    Long getBirthAddressCountryId();
    Long getGenderId();
    LocalDate getBirthDate();
    Long getCitizenshipTypeId();
    CitizenshipTypeAlias getCitizenshipTypeAlias();
    Long getCourtNationalityId();
    Long getBirthAddressCourtCountryId();
    Long getBirthAddressCourtRegionId();
    Long getBirthAddressCourtDistrictId();
    Long getActualAddressCourtRegionId();
    Long getActualAddressCourtDistrictId();
    String getActualAddressText();
    String getPostAddressText();
}
