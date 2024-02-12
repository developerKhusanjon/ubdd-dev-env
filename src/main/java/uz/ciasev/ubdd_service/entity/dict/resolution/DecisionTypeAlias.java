package uz.ciasev.ubdd_service.entity.dict.resolution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum DecisionTypeAlias implements BackendAlias {
    PUNISHMENT(1L),
    TERMINATION(2L),
    TRANSFER_TO_CRIMINAL_CASE(3L);

    private final long id;

    public static DecisionTypeAlias getInstanceById(Long id) {
        if (id == 1L) {
            return PUNISHMENT;
        } else if (id == 2L) {
            return TERMINATION;
        } else if (id == 3L) {
            return TRANSFER_TO_CRIMINAL_CASE;
        }
        throw new NotImplementedException(String.format("Enum value in DecisionTypeAlias for id %s not present", id));
    }

    public boolean is(DecisionTypeAlias alias) {
        return this.equals(alias);
    }

    public boolean not(DecisionTypeAlias alias) {
        return !this.equals(alias);
    }
}
