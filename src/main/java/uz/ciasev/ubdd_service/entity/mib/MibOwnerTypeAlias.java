package uz.ciasev.ubdd_service.entity.mib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum MibOwnerTypeAlias {
    DECISION(1L), COMPENSATION(2L), EVIDENCE_DECISION(3L);

    private final long id;

    public static MibOwnerTypeAlias getInstanceById(Integer id) {
        if (id == 1L) {
            return DECISION;
        } else if (id == 2L) {
            return COMPENSATION;
        } else if (id == 3L) {
            return EVIDENCE_DECISION;
        }
        throw new NotImplementedException(String.format("Enum value in MibOwnerTypeAlias for id %s not present", id));
    }

}
