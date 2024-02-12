package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

public enum PaymentTerms {
    DISCOUNT_50_70("50% 15 30% 30 NET 3650"),
    DISCOUNT("30% 15 NET 3650"),
    FULL_AMOUNT("NET 3650");

    private String value;

    PaymentTerms(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentTerms getByDiscountVersion(PenaltyPunishment.DiscountVersion discountVersion) {
        switch (discountVersion) {
            case NO: return FULL_AMOUNT;
            case V1: return DISCOUNT;
            case V2: return DISCOUNT_50_70;
            default: throw new ImplementationException(String.format("Unknown DiscountVersion %s for PaymentTerms calculation", discountVersion));
        }
    }
}
