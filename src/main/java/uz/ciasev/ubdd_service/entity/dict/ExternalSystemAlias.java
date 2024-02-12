package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ExternalSystemAliasCacheDeserializer;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = ExternalSystemAliasCacheDeserializer.class)
public enum ExternalSystemAlias implements BackendAlias {
    F1(1L),
    TECH_PASS(2L),
    CUSTOMS_EVENT(3L);

    private final long id;

    public static ExternalSystemAlias getInstanceById(Long id) {
        if (id == 1L) {
            return F1;
        } else if (id == 2L) {
            return TECH_PASS;
        } else if (id == 3L) {
            return CUSTOMS_EVENT;
        }
        throw new NotImplementedException(String.format("Enum value in ExternalSystemAlias for id %s not present", id));
    }
}
