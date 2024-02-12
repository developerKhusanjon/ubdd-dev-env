package uz.ciasev.ubdd_service.mvd_core.api.f1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class F1Document implements PersonDocument {

    private String id;
    private String pinpp;
    private String firstNameKir;
    private String secondNameKir;
    private String lastNameKir;

    private String firstNameLat;
    private String secondNameLat;
    private String lastNameLat;

    private LocalDate birthDate;
    private Long genderId;
    private Gender gender;

    private Long nationalityId;
    private Nationality nationality;

    private Address birthAddress;
    private Address givenAddress;
    private Address manzilAddress;
    private Address residentAddress;
//    private Long personDocumentTypeId;
    private PersonDocumentType personDocumentType;

    private String series;
    private String number;

    private LocalDate givenDate;
    private LocalDate expireDate;
    private BigInteger photoId;
    private String photoType;


    public Person buildPerson() {
        Person person = new Person();

        person.setPinpp(this.pinpp);
        person.setFirstNameKir(this.firstNameKir);
        person.setSecondNameKir(this.secondNameKir);
        person.setLastNameKir(this.lastNameKir);
        person.setFirstNameLat(this.firstNameLat);
        person.setSecondNameLat(this.secondNameLat);
        person.setLastNameLat(this.lastNameLat);
        person.setBirthDate(this.birthDate);
        person.setBirthAddress(this.birthAddress);
        person.setGender(this.gender);
        person.setNationality(this.nationality);

        return person;
    }

    public F1Document(GcpPersonInfo personInfo) {
        this.pinpp = personInfo.getPinpp();
        this.firstNameKir = personInfo.getFirstNameKir();
        this.secondNameKir = personInfo.getSecondNameKir();
        this.lastNameKir = personInfo.getLastNameKir();
        this.firstNameLat = personInfo.getFirstNameLat();
        this.secondNameLat = personInfo.getSecondNameLat();
        this.lastNameLat = personInfo.getLastNameLat();
        this.birthDate = personInfo.getBirthDate();
        this.series = personInfo.getSeries();
        this.number = personInfo.getNumber();
        this.givenDate = personInfo.getGivenDate();
        this.expireDate = personInfo.getExpireDate();
        this.photoId = personInfo.getPhotoId();
        this.photoType = personInfo.getPhotoType();
    }

    public Optional<String> getExternalId() {
        return Optional.of(this.pinpp);
    }

    public Optional<ExternalSystemAlias> getExternalSystem() {
        return Optional.of(ExternalSystemAlias.F1);
    }

    public Optional<Address> getManzilAddress() {
        return Optional.ofNullable(this.manzilAddress);
    }

    public Optional<Address> getResidentAddress() {
        return Optional.ofNullable(this.residentAddress);
    }


}
