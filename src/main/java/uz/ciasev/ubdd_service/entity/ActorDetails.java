package uz.ciasev.ubdd_service.entity;

import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;

import java.time.LocalDate;

public interface ActorDetails {

    void setExternalSystem(ExternalSystem externalSystem);
    void setExternalId(String externalId);
    void setF1Address(Address address);
    void setResidenceAddress(Address address);

    void setAgeCategory(AgeCategory ageCategory);
    void setPersonDocumentType(PersonDocumentType documentType);
    void setDocumentSeries(String series);
    void setDocumentNumber(String number);
    void setDocumentGivenDate(LocalDate givenDate);
    void setDocumentExpireDate(LocalDate expireDate);
    void setDocumentGivenAddress(Address givenAddress);
    //void setPhotoUri(String photoUri);
}
