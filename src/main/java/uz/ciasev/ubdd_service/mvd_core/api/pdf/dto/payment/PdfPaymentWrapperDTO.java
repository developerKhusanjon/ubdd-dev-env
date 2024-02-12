package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PdfPaymentWrapperDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private String fio;
    private String birthDate;
    private String article;
    private String prim;
    private String part;
    private String amount;
    private String amountText;
    private String invoiceType;
    private int status;

    private List<PdfPaymentDTO> payments = new ArrayList<>();

    public PdfPaymentWrapperDTO(AdmEntity admEntity) {
        super(admEntity);
    }

    public String getModelName() {
        return String.join("_", "payment", super.getModelName());
    }
}