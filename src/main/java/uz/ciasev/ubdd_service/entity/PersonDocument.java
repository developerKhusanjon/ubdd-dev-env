package uz.ciasev.ubdd_service.entity;

import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

public interface PersonDocument {

    PersonDocumentType getPersonDocumentType();

    String getSeries();

    String getNumber();

    Address getGivenAddress();

    LocalDate getGivenDate();

    LocalDate getExpireDate();

    Optional<Address> getManzilAddress();

    Optional<Address> getResidentAddress();

    Optional<ExternalSystemAlias> getExternalSystem();

    Optional<String> getExternalId();

    BigInteger getPhotoId();

    String getPhotoType();
}
