package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.Data;
import uz.ciasev.ubdd_service.utils.validator.ValidValueOrAllType;

@Data
@ValidValueOrAllType
public abstract class ValueOrAllType<T> {

    protected Boolean isAll;

    abstract public T getValue();

    abstract public boolean isValueEmpty();

    abstract public boolean isValuePresent();
}
