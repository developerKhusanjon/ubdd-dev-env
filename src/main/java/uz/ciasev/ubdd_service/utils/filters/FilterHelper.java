package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FilterHelper<E> extends AbstractFilterHelper<E> {

    @SafeVarargs
    public FilterHelper(Pair<String, Filter<E>>... filers) {
        this(List.of(filers));
    }

    public FilterHelper(Collection<Pair<String, Filter<E>>> filers) {
        super(filers);
    }

    public FilterHelper(Collection<Pair<String, Filter<E>>> filerList, Pair<String, Filter<E>>... filerArray) {
        super(
                Stream.of(filerList, List.of(filerArray))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Specification<E> getParamsSpecification(Map<String, String> paramValues) {
        List<Specification<E>> specificationList = buildSpecifications(paramValues).getFirst();
        return SpecificationsCombiner.andAll(specificationList);
    }
}
