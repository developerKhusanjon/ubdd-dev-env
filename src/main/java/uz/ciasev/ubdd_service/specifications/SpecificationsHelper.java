package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SpecificationsHelper {

    public static String likeValue(String value) {
        return "%" + value + "%";
    }

    public static String startWith(String value) {
        return value + "%";
    }

    public static <T> Specification<T> getEmpty() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Expression<LocalDate> toDate(CriteriaBuilder cb, Path<LocalDateTime> path) {
        return cb.function("date", LocalDate.class, path);
    }

    public static <T> Optional<Root<T>> getExistRoot(CriteriaQuery<?> query, Class<T> entityClass) {
        Optional<Root<?>> existRoot = query.getRoots().stream()
                .filter(r -> r.getModel().getJavaType().equals(entityClass))
                .findFirst();

        return existRoot
                .map(r -> (Root<T>) r);
    }

    private static <E, T> Optional<Join<E, T>> getExistJoin(From<?, E> root, String attribute) {
        //String atributs = root.getJoins().stream().map(j -> j.getAttribute().getName()).collect(Collectors.joining());

        Optional<Join<E, ?>> opt = root.getJoins().stream()
                .filter(j -> j.getAttribute().getName().equals(attribute))
                .findFirst();

        return opt.map(j -> (Join<E, T>) j);
    }

    public static <E, I extends E, T> Join<I, T> getExistJoin(From<?, I> root, SetAttribute<E, T> attribute) {
        Optional<Join<I, T>> existJoin = getExistJoin(root, attribute.getName());
        return existJoin.orElseGet(() -> root.join(attribute));
    }

    public static <E, I extends E, T> Join<I, T> getExistJoin(From<?, I> root, ListAttribute<E, T> attribute) {
        Optional<Join<I, T>> existJoin = getExistJoin(root, attribute.getName());
        return existJoin.orElseGet(() -> root.join(attribute));
    }

    public static <E, I extends E, T> Join<I, T> getExistJoin(From<?, I> root, SingularAttribute<E, T> attribute) {
        return getExistJoin(root, attribute, JoinType.INNER);
    }

    public static <E, I extends E, T> Join<I, T> getExistJoin(From<?, I> root, SingularAttribute<E, T> attribute, JoinType joinType) {
        Optional<Join<I, T>> existJoin = getExistJoin(root, attribute.getName());
        return existJoin.orElseGet(() -> root.join(attribute, joinType));
    }

    public static List<Selection<?>> getSortingColumns(Root root, Sort sort) {
        List<Selection<?>> selectedColumns = new ArrayList<>();
        if (sort != null && sort.isSorted()) {
            sort.stream().forEach(order -> {
                selectedColumns.add(root.get(order.getProperty()));
            });
        }
        return selectedColumns;
    }

    public static <T extends Query> T setPage(T executableQuery, Pageable pageable) {
        if (pageable != null && pageable.isPaged()) {
            executableQuery.setMaxResults(pageable.getPageSize());
            executableQuery.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
        }
        return executableQuery;
    }

    public static <T> Predicate getExactlyLongSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Long> attribute, Long value) {
        if (value == null) {
            return cb.isNull(root.get(attribute));
        }
        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getNullableLongSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Long> attribute, Long value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getNullableIntSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Integer> attribute, Integer value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getExactlyLongInSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Long> attribute, Long value) {
        if (value == null) {
            return root.get(attribute).isNull();
        }

        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getNullableLongInSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Long> attribute, Collection<Long> value) {
        if (value == null || value.isEmpty()) {
            return cb.conjunction();
        }
        return root.get(attribute).in(value);
    }

    public static <T> Predicate getNullableBoolSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, Boolean> attribute, Boolean value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getNullableStringSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, String> attribute, String value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.equal(root.get(attribute), value);
    }

    public static <T> Predicate getNullableDateLessSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDate> attribute, LocalDate value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    public static <T> Predicate getNullableDateTimeLessSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDateTime> attribute, LocalDate value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.lessThanOrEqualTo(
                SpecificationsHelper.toDate(cb, root.get(attribute)),
                value
        );
    }

    public static <T> Predicate getNullableDateTimeLessSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDateTime> attribute, LocalDateTime value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    public static <T> Predicate getNullableDateGreatSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDate> attribute, LocalDate value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    public static <T> Predicate getNullableDateTimeGreatSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDateTime> attribute, LocalDate value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.greaterThanOrEqualTo(
                SpecificationsHelper.toDate(cb, root.get(attribute)),
                value
        );
    }

    public static <T> Predicate getNullableDateTimeGreatSpecification(Root<T> root, CriteriaBuilder cb, SingularAttribute<T, LocalDateTime> attribute, LocalDateTime value) {
        if (value == null) {
            return cb.conjunction();
        }
        return cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

}
