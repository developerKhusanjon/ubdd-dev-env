package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AdvancedFilterHelper<E> extends AbstractFilterHelper<E> {

    @SafeVarargs
    public AdvancedFilterHelper(Pair<String, Filter<E>>... filers) {
        this(List.of(filers));
    }

    public AdvancedFilterHelper(Collection<Pair<String, Filter<E>>> filers) {
        super(filers);
        if (filers.size() == 0) {
            throw new RuntimeException("No present filters for RequiredFilterHelper");
        }
    }

    public Specification<E> getParamsSpecification(Map<String, String> paramValues, Consumer<Pair<List<Specification<E>>, Integer>> advance) {
        Pair<List<Specification<E>>, Integer> specificationListAndParamCount = buildSpecifications(paramValues);

        advance.accept(specificationListAndParamCount);

        return SpecificationsCombiner.andAll(specificationListAndParamCount.getFirst());
    }
}
