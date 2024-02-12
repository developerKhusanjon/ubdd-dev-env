package uz.ciasev.ubdd_service.entity.single_thread_operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum SingleThreadOperationTypeAlias {
    DECISION_TO_MIB(1),
    ADM_CASE_TO_COURT(2),
    SYNC_DECISION_WITH_MIB(3);

    private final long id;

    public static SingleThreadOperationTypeAlias getInstanceById(Long id) {
        if (id == 1L) {
            return DECISION_TO_MIB;
        } else if (id == 2L) {
            return ADM_CASE_TO_COURT;
        } else if (id == 3L) {
            return SYNC_DECISION_WITH_MIB;
        }
        throw new NotImplementedException(String.format("Enum value in SingleThreadOperationTypeAlias for id %s not present", id));
    }
}
