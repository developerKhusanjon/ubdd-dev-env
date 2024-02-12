package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfMailDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private PdfPostDataDTO post;
    private PdfKarorDTO karor;
    private PdfViolatorDTO violator;
    private PdfViolationDTO violation;
    private PdfInvoiceDTO invoice;
    private PdfInvoiceDTO damageInvoice;

    public PdfMailDTO(AdmEntity admEntity) {
        super(admEntity);
    }

    public String getModelName() {
        return String.join("_", "mail", super.getModelName());
    }
}

