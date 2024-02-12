package uz.ciasev.ubdd_service.config.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CiasevDBConstraint {

    UniqueActiveResolutionInCase("resolution_case_id_is_active"),
    UniqueMibCardMovementRequestId("mib_card_movement_request_id_uniq"),
    UniqueResourceIdOperationTypeIdPair("resource_id_and_operation_type_id_unique_together");

    private final String dbName;
}
