package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class BooleanFilter<E> extends AbstractFilter<Boolean, E> {

    public BooleanFilter(Function<Boolean, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
    }

    @Override
    protected Boolean serialize(String value) {
        return Boolean.valueOf(value);
    }
}
