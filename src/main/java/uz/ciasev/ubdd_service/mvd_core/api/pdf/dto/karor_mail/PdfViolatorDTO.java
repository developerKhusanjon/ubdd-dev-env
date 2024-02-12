package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Data;

import java.util.List;

@Data
public class PdfViolatorDTO {

    private String fullName;
    private String birthDate;
    private String phone;
    private String birthPlace;
    private String documentSeriesAndNumber;
    private String nationality;
    private String citizenship;
    private String residentAddress;
    private String occupation;
    private String photo;
    private String insurance = "yo'q";
    private String transport = "yo'q";
    private String additionalInfo = "yo'q";
    private String intoxication = "yo'q";
    private List<String> repeatability;
    private String pinpp;
}