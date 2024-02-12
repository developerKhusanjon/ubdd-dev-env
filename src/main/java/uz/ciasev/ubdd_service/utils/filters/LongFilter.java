package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class LongFilter<E> extends AbstractFilter<Long, E> {

    public LongFilter(Function<Long, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
    }

    @Override
    protected Long serialize(String value) {
        return Long.valueOf(value);
    }
}
