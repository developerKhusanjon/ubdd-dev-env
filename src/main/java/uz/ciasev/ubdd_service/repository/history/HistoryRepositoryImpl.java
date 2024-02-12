package uz.ciasev.ubdd_service.repository.history;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryViewRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public <T> Page<T> findAllPageable(Class<T> entityClass, Pageable pageable) {
        return findAllPageableBySpecification(entityClass, SpecificationsHelper.getEmpty(), pageable);
    }

    @Override
    public <T> Page<T> findAllPageableBySpecification(Class<T> entityClass, Specification<T> specification, Pageable pageable) {
        Long count = getCount(entityClass, specification);
        List<T> results = getResults(entityClass, pageable, specification);

        return new PageImpl<>(results, pageable, count);
    }

    private <T> Long getCount(Class<T> entityClass, Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);

        countQuery.select(criteriaBuilder.countDistinct(countRoot))
                .where(specification.toPredicate(countRoot, countQuery, criteriaBuilder));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private <T> List<T> getResults(Class<T> entityClass, Pageable pageable, Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> mainQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> resultsRoot = mainQuery.from(entityClass);
        Predicate specificationPredicate = specification.toPredicate(resultsRoot, mainQuery, criteriaBuilder);

        mainQuery.select(resultsRoot).where(specificationPredicate);

        TypedQuery<T> resultsQuery = entityManager.createQuery(mainQuery);

        return SpecificationsHelper.setPage(resultsQuery, pageable).getResultList();
    }
}
