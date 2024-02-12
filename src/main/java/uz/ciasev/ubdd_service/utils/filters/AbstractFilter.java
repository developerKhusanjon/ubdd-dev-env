package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public abstract class AbstractFilter<T, E> implements Filter<E> {

    protected final Function<T, Specification<E>> specificationSupplier;

    public AbstractFilter(Function<T, Specification<E>> specificationSupplier) {
        this.specificationSupplier = specificationSupplier;
    }

    protected abstract T serialize(String value);

    public Specification<E> getSpecification(String value) {
        T serializedValue = null;

        if (value != null && !value.isEmpty() && !value.isBlank()) {
            try {
                serializedValue = serialize(value);
            } catch (Exception e) {
            }
        }

        return specificationSupplier.apply(serializedValue);
    }
}
