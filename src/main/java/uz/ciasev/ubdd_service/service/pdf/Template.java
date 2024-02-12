package uz.ciasev.ubdd_service.service.pdf;

public enum Template {
    KAROR_AND_MAIL("karor"),
    REQUIREMENT("pechat"),
    INVOICE_FOR_COURT("kvitanciyaSud"),
    PERSON_CARD_FOR_COURT("sud"),
    PROTOCOL("protocol"),
    TERMINATION_DECISION("determination"),
    PAYMENTS("payment"),
    VIOLATOR_PAYMENTS("violator-payment"),
    SMS("sms-notification-mib"),
    RETURN_REQUEST("mib-return-request");

    private final String value;

    Template(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
