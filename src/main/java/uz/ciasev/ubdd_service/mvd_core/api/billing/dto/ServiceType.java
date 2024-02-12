package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

public enum ServiceType {
    COMPENSATION(907L),
    PENALTY(906L);

    private final Long value;

    ServiceType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
