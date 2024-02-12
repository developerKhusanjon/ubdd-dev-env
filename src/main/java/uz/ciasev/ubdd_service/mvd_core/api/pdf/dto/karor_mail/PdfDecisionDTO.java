package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfDecisionDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private PdfKarorDTO karor;
    private PdfViolatorDTO violator;
    private PdfViolationDTO violation;
    private PdfInvoiceDTO invoice;
    private PdfInvoiceDTO damageInvoice;

    private String autoCreateId;

    public PdfDecisionDTO(AdmEntity admEntity) {
        super(admEntity);
    }
}
