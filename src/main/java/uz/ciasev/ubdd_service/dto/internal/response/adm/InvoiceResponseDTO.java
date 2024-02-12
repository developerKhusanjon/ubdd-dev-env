package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InvoiceResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final boolean isActive;
    @Deprecated private final Boolean isDiscount;
    private final Boolean isDiscount50;
    private final Boolean isDiscount70;
    private final LocalDateTime deactivateTime;
    private final String deactivateReason;
    private final Long ownerTypeId;
    private final Long penaltyPunishmentId;
    private final Long compensationId;
    private final Long damageId;
    private final Long damageSettlementId;
    private final String invoiceSerial;
    private final LocalDate invoiceDate;
    @Deprecated private final LocalDate discountForDate;
    private final LocalDate discount50ForDate;
    private final LocalDate discount70ForDate;
    private final Long amount;
    @Deprecated private final Long discountAmount;
    private final Long discount50Amount;
    private final Long discount70Amount;
    private final String organName;
    private final String organInn;
    private final String bankName;
    private final String bankCode;
    private final String bankAccount;
    private final String treasuryAccount;
    private final String payerName;
    private final String payerAddress;
    private final LocalDate payerBirthdate;
    private final String payerInn;
    private final ApiUrl pdfUrl;

    public InvoiceResponseDTO(Invoice invoice) {
        this.id = invoice.getId();
        this.createdTime = invoice.getCreatedTime();
        this.editedTime = invoice.getEditedTime();
        this.isActive = invoice.isActive();
        this.deactivateTime = invoice.getDeactivateTime();
        this.deactivateReason = invoice.getDeactivateReasonDesc();
        this.ownerTypeId = invoice.getOwnerTypeId();
        this.penaltyPunishmentId = invoice.getPenaltyPunishmentId();
        this.compensationId = invoice.getCompensationId();
        this.damageId = null;
        this.damageSettlementId = invoice.getDamageSettlementDetailId();
        this.invoiceSerial = invoice.getInvoiceSerial();
        this.invoiceDate = invoice.getInvoiceDate();
        this.amount = invoice.getAmount();
        this.isDiscount = invoice.getIsDiscount70();
        this.discountAmount = invoice.getDiscount70Amount();
        this.discountForDate = invoice.getDiscount70ForDate();
        this.isDiscount50 = invoice.getIsDiscount50();
        this.isDiscount70 = invoice.getIsDiscount70();
        this.discount50Amount = invoice.getDiscount50Amount();
        this.discount70Amount = invoice.getDiscount70Amount();
        this.discount50ForDate = invoice.getDiscount50ForDate();
        this.discount70ForDate = invoice.getDiscount70ForDate();
        this.organName = invoice.getOrganName();
        this.organInn = invoice.getBankInn();
        this.bankName = invoice.getBankName();
        this.bankCode = invoice.getBankCode();
        this.bankAccount = invoice.getBankAccount();
        this.treasuryAccount = invoice.getTreasuryAccount();
        this.payerName = invoice.getPayerName();
        this.payerAddress = invoice.getPayerAddress();
        this.payerBirthdate = invoice.getPayerBirthdate();
        this.payerInn = invoice.getPayerInn();

        this.pdfUrl = ApiUrl.getInvoiceInstance(invoice);

    }
}
