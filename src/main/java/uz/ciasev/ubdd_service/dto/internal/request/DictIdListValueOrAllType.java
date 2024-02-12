package uz.ciasev.ubdd_service.dto.internal.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictIdListValueOrAllType<T extends AbstractDict> extends ValueOrAllType<List<T>> {

    @JsonProperty("ids")
    protected List<T> value;

    @Override
    public boolean isValueEmpty() {
        return value == null || value.isEmpty();
    }

    @Override
    public boolean isValuePresent() {
        return value != null && !value.isEmpty();
    }
}
