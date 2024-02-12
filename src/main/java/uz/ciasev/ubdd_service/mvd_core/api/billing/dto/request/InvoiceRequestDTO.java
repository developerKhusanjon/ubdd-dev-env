package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.PayerType;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.PaymentTerms;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.ServiceType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * lfName (string)          - size (100)    required = true
 * inn (string)             - size (9)      required = false
 * email (string)           - size (100)    required = false
 * phone (string)           - size (20)     required = false
 * type (string)            - enum          required = true (0, 1, 2)
 * passport (string)        - size (9)      required = false
 * <p>
 * invoiceNumber (string)   - size (50)     required = true     UNIQUE NUMBER
 * departmentId (long)      - size (9)      required = true
 * issueDate (date)         -               required = true
 * amount (long)            - size (12)     required = true
 * <p>
 * serviceId (long)         - size(9)       required = true
 * paymentTerms (string)    - enum          required = true
 */
@Data
public class InvoiceRequestDTO {

    //  Payer's fields
    @NotNull(message = "PAYER_LAST_AND_FIRST_NAME_REQUIRED")
    private String lfName;
    private String inn;
    private String email;
    private String phone;
    @NotNull(message = "PAYER_TYPE_REQUIRED")
    private PayerType type;
    private String passport;

    // Contract's fields
    @NotNull(message = "INVOICE_NUMBER_REQUIRED")
    private String invoiceNumber;
    @NotNull(message = "CONTRACT_DEPARTMENT_ID_REQUIRED")
    private Long departmentId;
    @NotNull(message = "CONTRACT_AMOUNT_REQUIRED")
    private Double amount;
    @NotNull(message = "ISSUE_DATE_REQUIRED")
    private LocalDate issueDate = LocalDate.now();

    // Invoice's fields
    @NotNull(message = "SERVICE_ID_REQUIRED")
    private ServiceType serviceId;
    @NotNull(message = "PAYMENT_TERMS_REQUIRED")
    private PaymentTerms paymentTerms;
}
