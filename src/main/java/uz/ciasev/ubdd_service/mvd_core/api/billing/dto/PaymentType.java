package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentType {
    @JsonProperty("payment.created")
    CREATED,
    @JsonProperty("invoice.paid")
    PAID
}
