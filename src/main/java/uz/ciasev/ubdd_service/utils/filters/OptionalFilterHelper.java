package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OptionalFilterHelper<E> extends AbstractFilterHelper<E> {

    @SafeVarargs
    public OptionalFilterHelper(Pair<String, Filter<E>>... filers) {
        super(List.of(filers));
    }


    public Optional<Specification<E>> getParamsSpecificationOptional(Map<String, String> paramValues) {
        Pair<List<Specification<E>>, Integer> specificationListAndParamCount = buildSpecifications(paramValues);

        if (specificationListAndParamCount.getSecond() == 0) {
            return Optional.empty();
        }

        return Optional.of(SpecificationsCombiner.andAll(specificationListAndParamCount.getFirst()));
    }

    @Override
    public Specification<E> getParamsSpecification(Map<String, String> paramValues) {
        return getParamsSpecificationOptional(paramValues)
                .orElseGet(SpecificationsHelper::getEmpty);
    }
}
