package uz.ciasev.ubdd_service.entity.dict.mib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.utils.deserializer.dict.MibCaseStatusAliasDeserializer;


@Getter
@AllArgsConstructor
@JsonDeserialize(using = MibCaseStatusAliasDeserializer.class)
public enum MibCaseStatusAlias implements BackendAlias {
    ACCEPTED(1L),
    RETURN(2L),
    EXECUTED(3L),
    TERMINATION(4L),
    PAID(5L);

    private final long id;

    public static MibCaseStatusAlias getInstanceById(Long id) {
        if (id == 1L) {
            return ACCEPTED;
        } else if (id == 2L) {
            return RETURN;
        } else if (id == 3L) {
            return EXECUTED;
        } else if (id == 4L) {
            return TERMINATION;
        } else if (id == 5L) {
            return PAID;
        }
        throw new NotImplementedException(String.format("Enum value in MibCaseStatusAlias for id %s not present", id));
    }
}
