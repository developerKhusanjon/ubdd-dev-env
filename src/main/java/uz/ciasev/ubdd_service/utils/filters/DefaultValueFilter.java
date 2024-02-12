package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public abstract class DefaultValueFilter<T, E> extends AbstractFilter<T, E> {

    private final T defaultValue;

    public DefaultValueFilter(T defaultValue, Function<T, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
        this.defaultValue = defaultValue;
    }

    public Specification<E> getSpecification(String value) {
        T serializedValue = defaultValue;

        if (value != null && !value.isEmpty() && !value.isBlank()) {
            try {
                serializedValue = serialize(value);
            } catch (Exception e) {
            }
        }

        return specificationSupplier.apply(serializedValue);
    }
}
