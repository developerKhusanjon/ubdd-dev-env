package uz.ciasev.ubdd_service.entity.publicapi;

public enum PublicApiWebhookType {
    INVOICE,
    PAYMENT,
    COURT,
    @Deprecated
    MIB,
    MIB_SEND,
    MIB_VALIDATION,
    MIB_ACCEPTED,
    MIB_PAID,
    MIB_RETURN,
    MIB_EXECUTED,
    MIB_CANCEL_EXECUTION,
    ADM_CASE_STATUS_CHANGE,
    DECISION_STATUS_CHANGE,
    PUNISHMENT_STATUS_CHANGE,
    COMPENSATION_STATUS_CHANGE,
    DECISION_CANCELED,
    DECISION_MADE,
}
