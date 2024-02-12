package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractFilterHelper<E> {

    private final Map<String, Filter<E>> filtersMap;
    private final Map<String, Filter<E>> defaultValueFilterMap;

    public AbstractFilterHelper(Collection<Pair<String, Filter<E>>> filers) {

        this.filtersMap = new HashMap<>();
        this.defaultValueFilterMap = new HashMap<>();

        if (filers == null) {
            return;
        }

        for (Pair<String, Filter<E>> filer : filers) {
            if (filer.getSecond() instanceof DefaultValueFilter) {
                defaultValueFilterMap.put(filer.getFirst(), filer.getSecond());
            } else {
                filtersMap.put(filer.getFirst(), filer.getSecond());
            }
        }
    }

    public abstract Specification<E> getParamsSpecification(Map<String, String> paramValues);

    protected Pair<List<Specification<E>>, Integer> buildSpecifications(Map<String, String> paramValues) {
        List<Specification<E>> specificationList = new ArrayList<>();
        AtomicInteger paramCount = new AtomicInteger();

        defaultValueFilterMap.forEach((key, filter) -> {

            String value = paramValues.get(key);
            if (value != null) {
                paramCount.getAndIncrement();
            }

            Specification<E> paramSpecification = filter.getSpecification(value);
            specificationList.add(paramSpecification);
        });

        if (paramValues == null) {
            return Pair.of(specificationList, paramCount.get());
        }

        paramValues.forEach((key, value) -> {
            Filter<E> filter = filtersMap.get(key);
            if (filter == null) return;

            if (value != null) {
                paramCount.getAndIncrement();
            }

            Specification<E> paramSpecification = filter.getSpecification(value);
            specificationList.add(paramSpecification);
        });


//         todo заплатка
//        specificationList.add(SpecificationsHelper.getEmpty());

        return Pair.of(specificationList, paramCount.get());
    }

}
