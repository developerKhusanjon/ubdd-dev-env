package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class StringFilter<E> extends AbstractFilter<String, E> {

    public StringFilter(Function<String, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
    }

    @Override
    protected String serialize(String value) {
        return value;
    }
}
