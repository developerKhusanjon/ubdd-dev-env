package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.invoice;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfInvoiceDTO;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfInvoiceForCourtDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private String series;
    private String number;
    private String date;
    private String time;
    private String ownerType;
    private PdfInvoiceDTO invoice;

    public PdfInvoiceForCourtDTO(AdmEntity admEntity) {
        super(admEntity);
    }
}
