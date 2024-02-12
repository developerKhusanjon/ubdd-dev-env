package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class DefaultValueBooleanFilter<E> extends DefaultValueFilter<Boolean, E> {

    public DefaultValueBooleanFilter(boolean defaultValue, Function<Boolean, Specification<E>> specificationSupplier) {
        super(defaultValue, specificationSupplier);
    }

    @Override
    protected Boolean serialize(String value) {
        return Boolean.valueOf(value);
    }
}
