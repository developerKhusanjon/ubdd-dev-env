package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfSmsDTO extends PdfModel {

    private String createdDate;
    private String createdTime;
    private String sendDate;
    private String receiveDate;
    private String phoneNumber;
    private String smsText;

    public PdfSmsDTO(AdmEntity admEntity) {
        super(admEntity);
    }

}
