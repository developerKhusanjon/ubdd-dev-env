package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.exception.SearchFiltersNotSetException;

import java.util.Collection;
import java.util.Map;

public class RequiredFilterHelper<E> extends AdvancedFilterHelper<E> {

    @SafeVarargs
    public RequiredFilterHelper(Pair<String, Filter<E>>... filers) {
        super(filers);
    }

    public RequiredFilterHelper(Collection<Pair<String, Filter<E>>> filers) {
        super(filers);
    }

    public Specification<E> getParamsSpecification(Map<String, String> paramValues, boolean checkRequirements) {
        return super.getParamsSpecification(
                paramValues,
                specificationListAndParamCount -> {
                    if (checkRequirements && specificationListAndParamCount.getSecond() == 0) {
                        throw new SearchFiltersNotSetException();
                    }
                }
        );
    }

    @Override
    public Specification<E> getParamsSpecification(Map<String, String> paramValues) {
        return getParamsSpecification(paramValues, true);
    }
}
