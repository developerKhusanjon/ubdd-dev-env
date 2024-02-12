package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetFilter<E> extends AbstractFilter<Set<Long>, E> {

    public SetFilter(Function<Set<Long>, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
    }

    @Override
    protected Set<Long> serialize(String value) {
        Set<Long> values = null;

        if (value != null) {
            values = Arrays.stream(value.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        }

        return values;
    }
}
