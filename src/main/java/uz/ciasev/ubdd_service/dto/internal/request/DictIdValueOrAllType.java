package uz.ciasev.ubdd_service.dto.internal.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictIdValueOrAllType<T extends AbstractDict> extends ValueOrAllType<T> {

    @JsonProperty("id")
    T value;

    @Override
    public boolean isValueEmpty() {
        return value == null;
    }

    @Override
    public boolean isValuePresent() {
        return value != null;
    }
}
