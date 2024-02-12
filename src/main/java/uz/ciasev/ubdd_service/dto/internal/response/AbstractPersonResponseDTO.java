package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;

import java.time.LocalDate;

@Getter
public abstract class AbstractPersonResponseDTO {

    private final String pinpp;
    private final boolean isRealPinpp;
    private final String firstNameKir;
    private final String secondNameKir;
    private final String lastNameKir;
    private final String firstNameLat;
    private final String secondNameLat;
    private final String lastNameLat;
    private final LocalDate birthDate;
    private final Long birthAddressId;
    private final Long citizenshipTypeId;
    private final Long genderId;
    private final Long nationalityId;

    public AbstractPersonResponseDTO(Person person) {
        this.pinpp = person.getPinpp();
        this.isRealPinpp = person.isRealPinpp();
        this.firstNameKir = person.getFirstNameKir();
        this.secondNameKir = person.getSecondNameKir();
        this.lastNameKir = person.getLastNameKir();
        this.firstNameLat = person.getFirstNameLat();
        this.secondNameLat = person.getSecondNameLat();
        this.lastNameLat = person.getLastNameLat();
        this.birthDate = person.getBirthDate();
        this.birthAddressId = person.getBirthAddressId();
        this.citizenshipTypeId = person.getCitizenshipTypeId();
        this.genderId = person.getGenderId();
        this.nationalityId = person.getNationalityId();
    }
}