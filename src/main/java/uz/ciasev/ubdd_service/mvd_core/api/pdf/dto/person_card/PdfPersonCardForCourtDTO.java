package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.person_card;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfPersonCardForCourtDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private String pinpp;  // "12345543212345",
    private String fullName;  // "KIM IGOR VOLLEROVICH",
    private String birthDate;  // "30.09.1999",
    private String birthAddress;  // "adres rojdeniya",
    private String gender;  // "Mujskoy",
    private String nationality;  // "Korees",
    private String citizenship;  // "UZBEKISTAN",
    private String residenceAddress;  // "adres projivaniya",
    private String documentType;  // "tip odcumenta",
    private String documentSeriesAndNumber;  // "AA 1234567",
    private String givingDate;  // "20.09.1990",
    private String givingAddress;  // "Tashkent, Uzbekistan, lisunove 40-25"
    private String expiresAt;  // "30.09.1999",
    private String photoUrl;

    public PdfPersonCardForCourtDTO(AdmEntity admEntity) {
        super(admEntity);
    }
}
