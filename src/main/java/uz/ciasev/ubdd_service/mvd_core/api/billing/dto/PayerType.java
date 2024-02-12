package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

public enum PayerType {
    LEGAL("0"),
    PERSON("1"),
    FOREIGNER("2");

    private final String value;

    PayerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
