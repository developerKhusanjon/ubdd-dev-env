package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.GcpPersonInfo;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class F1DocumentListDTO {

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
    private Long nationalityId;
    private Long personDocumentTypeId;
    private AddressResponseDTO birthAddress;
    private AddressResponseDTO givenAddress;
    private AddressResponseDTO residentAddress;
    private AddressResponseDTO manzilAddress;
    private String series;
    private String number;
    private LocalDate givenDate;
    private LocalDate expireDate;
    private String personPhotoUrl;

    public F1DocumentListDTO(GcpPersonInfo personInfo) {
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
        this.genderId = personInfo.getGenderId();
        this.nationalityId = personInfo.getNationalityId();
        this.personDocumentTypeId = personInfo.getPersonDocumentTypeId();
    }
}
