package uz.ciasev.ubdd_service.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum NotificationTypeAlias implements BackendAlias {
    //ADM
    PROTOCOL_CREATE(1L),
    SEND_ADM_CASE_TO_ORGAN(2L),
    RETURN_ADM_CASE_FROM_ORGAN(3L),
    RESOLUTION_CREATE(4L),

    //COURT
    REGISTERED_IN_COURT(5L),
    NOT_REGISTERED_IN_COURT(6L),
    COURT_RESOLUTION_CREATE(7L),
    COURT_RETURN_ADM_CASE(8L),
    REGISTERED_MATERIAL_315_IN_COURT(9L),

    //MIB
    MIB_PRE_SEND(10L),
    MIB_RETURN_DECISION(11L),

    //USER
    DIGITAL_SIGNATURE_PASSWORD(12L);

    public static final String MIB_PRE_SEND_ID = "10";

    private final long id;

    public static NotificationTypeAlias getInstanceById(Long id) {
        if (id == 1) {
            return PROTOCOL_CREATE;
        } else if (id == 2) {
            return SEND_ADM_CASE_TO_ORGAN;
        } else if (id == 3) {
            return RETURN_ADM_CASE_FROM_ORGAN;
        } else if (id == 4) {
            return RESOLUTION_CREATE;
        } else if (id == 5) {
            return REGISTERED_IN_COURT;
        } else if (id == 6) {
            return NOT_REGISTERED_IN_COURT;
        } else if (id == 7) {
            return COURT_RESOLUTION_CREATE;
        } else if (id == 8) {
            return COURT_RETURN_ADM_CASE;
        } else if (id == 9) {
            return REGISTERED_MATERIAL_315_IN_COURT;
        } else if (id == 10) {
            return MIB_PRE_SEND;
        } else if (id == 11) {
            return MIB_RETURN_DECISION;
        } else if (id == 12) {
            return DIGITAL_SIGNATURE_PASSWORD;
        }
        throw new NotImplementedException(String.format("Enum value in NotificationTypeAlias for id %s not present", id));
    }
}
