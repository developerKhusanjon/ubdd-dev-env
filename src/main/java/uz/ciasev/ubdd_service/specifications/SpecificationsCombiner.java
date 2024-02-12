package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpecificationsCombiner {

    @SafeVarargs
    public static <T> Specification<T> andAll(Specification<T>... specifications) {
        return andAll(Arrays.stream(specifications).collect(Collectors.toList()));
    }

    public static <T> Specification<T> andAll(List<Specification<T>> specifications) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Specification<T>> notNullSpec = specifications.stream().filter(Objects::nonNull).collect(Collectors.toList());

                if (notNullSpec.size() == 0) {
                    return cb.conjunction();
                }

                if (notNullSpec.size() == 1) {
                    return notNullSpec.get(0).toPredicate(root, query, cb);
                } else {
                    Specification<T> resSpec = notNullSpec.get(0);

                    for (Specification<T> nextSpec : notNullSpec.subList(1, notNullSpec.size())) {
                        resSpec = resSpec.and(nextSpec);
                    }

                    return resSpec.toPredicate(root, query, cb);
                }

            }
        };
    }

    @SafeVarargs
    public static <T> Specification<T> orAll(Specification<T>... specifications) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Specification<T>> notNullSpec = Arrays.stream(specifications).filter(Objects::nonNull).collect(Collectors.toList());

                if (notNullSpec.size() == 0) {
                    return cb.conjunction();
                }

                if (notNullSpec.size() == 1) {
                    return notNullSpec.get(0).toPredicate(root, query, cb);
                } else {
                    Specification<T> resSpec = notNullSpec.get(0);

                    for (Specification<T> nextSpec : notNullSpec.subList(1, notNullSpec.size())) {
                        resSpec = resSpec.or(nextSpec);
                    }

                    return resSpec.toPredicate(root, query, cb);
                }

            }
        };
    }

}
