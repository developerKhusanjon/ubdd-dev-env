package uz.ciasev.ubdd_service.entity.dict.violation_event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum ViolationEventResultTypeAlias implements BackendAlias {

    ANNULMENT(1L),
    DECISION(2L);

    private final long id;

    public static ViolationEventResultTypeAlias getInstanceById(Long id) {
        if (id == 1) {
            return ANNULMENT;
        } else if (id == 2) {
            return DECISION;
        }
        throw new NotImplementedException(String.format("Enum value in ViolationEventResultTypeAlias for id %s not present", id));
    }
}
