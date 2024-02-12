package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.PayerDTO;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiInvoiceResponseDTO {

    private Long id;
    private String requestId;
    private String serial;
    private Long balance;
    private String status;
    private String bankAccount;
    private String budgetAccount;
    private String mfo;
    private BillingContractResponseDTO contract;
    private PayerDTO payer;
    private BillingBankResponseDTO bank;
    private String paymentTerms;
    private LocalDate issueDate;
    private LocalDate expireDate;

    @JsonProperty("earlyPaymentDiscounts")
    private List<EarlyDiscountResponseDTO> earlyPaymentDiscounts;

    // старые поля. Нужны что бы можно было запускаться, не дожидаясь обновления от биллинга
    private Long fee;
    @JsonProperty("earlyPaymentDiscountDue")
    private LocalDate feeDate;

    private List<BillingPaymentDTO> payments = new ArrayList<>();

    private Optional<EarlyDiscountResponseDTO> getDiscount50(PenaltyPunishment.DiscountVersion version) {
        return getDiscountsData().stream().findFirst();
    }

    private Optional<EarlyDiscountResponseDTO> getDiscount70(PenaltyPunishment.DiscountVersion version) {
        List<EarlyDiscountResponseDTO> discounts = getDiscountsData();

        if (version.equals(PenaltyPunishment.DiscountVersion.V1)) {
            return getDiscountsData().stream().findFirst(); // верни первую скидку
        }

        if (version.equals(PenaltyPunishment.DiscountVersion.V2)) {
            if (discounts.size() < 2) {
                return Optional.empty();
            }
            return Optional.ofNullable(discounts.get(1)); // верни вторую скидку
        }

        return Optional.empty();

    }

    public List<EarlyDiscountResponseDTO> getDiscountsData() {
        if (earlyPaymentDiscounts == null) {
            return List.of();
        }
        return earlyPaymentDiscounts;
    }

    public Optional<LocalDate> getFee50Date(PenaltyPunishment.DiscountVersion discount) {
        return getDiscount50(discount).map(EarlyDiscountResponseDTO::getFeeDate);
    }

    public Optional<LocalDate> getFee70Date(PenaltyPunishment.DiscountVersion discount) {
        Optional<LocalDate> newStyleValue = getDiscount70(discount).map(EarlyDiscountResponseDTO::getFeeDate);
        if (newStyleValue.isEmpty()) {
            return Optional.ofNullable(feeDate);
        }
        return newStyleValue;
    }

    public Optional<Long> getFee50(PenaltyPunishment.DiscountVersion discount) {
        return getDiscount50(discount).map(EarlyDiscountResponseDTO::getFee);
    }

    public Optional<Long> getFee70(PenaltyPunishment.DiscountVersion discount) {
        Optional<Long> newStyleValue = getDiscount70(discount).map(EarlyDiscountResponseDTO::getFee);
        if (newStyleValue.isEmpty()) {
            return Optional.ofNullable(fee);
        }
        return newStyleValue;
    }
}
