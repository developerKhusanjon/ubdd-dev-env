package uz.ciasev.ubdd_service.repository.protocol;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import uz.ciasev.ubdd_service.entity.CiasevEntity;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class EntityRepositoryImpl<T extends CiasevEntity> implements EntityRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;
    protected final   Class<T> entityClass;
    protected final SingularAttribute<T, Long> entityId;

    @Override
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }

    @Override
    public Page<Long> findAllId(Specification<T> specification, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQq = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQq.from(entityClass);

        countQq.select(criteriaBuilder.countDistinct(countRoot))
                .where(specification.toPredicate(countRoot, countQq, criteriaBuilder));
        Long count = entityManager.createQuery(countQq).getSingleResult();


        CriteriaQuery<Tuple> cq = criteriaBuilder.createQuery(Tuple.class).distinct(true);
        Root<T> root = cq.from(entityClass);

        Path<Long> idField = root.get(entityId);

        List<Selection<?>> selectedColumns = SpecificationsHelper.getSortingColumns(root, pageable.getSort());
        selectedColumns.add(idField);

        cq.multiselect(selectedColumns)
                .where(specification.toPredicate(root, cq, criteriaBuilder))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, criteriaBuilder));


        TypedQuery<Tuple> executableQuery = entityManager.createQuery(cq);

        List<Long> result = SpecificationsHelper.setPage(executableQuery, pageable)
                .getResultList().stream()
                .map(tuple -> tuple.get(idField))
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Long> getPagination(Specification<T> specification, Pageable pageable) {
        long count = getCount(specification);
        return new PageImpl<>(List.of(), pageable, count);
    }

    @Override
    public List<Long> findAllIdList(Specification<T> specification, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = criteriaBuilder.createQuery(Tuple.class).distinct(true);
        Root<T> root = cq.from(entityClass);

        Path<Long> idField = root.get(entityId);

        List<Selection<?>> selectedColumns = SpecificationsHelper.getSortingColumns(root, pageable.getSort());
        selectedColumns.add(idField);

        cq.multiselect(selectedColumns)
                .where(specification.toPredicate(root, cq, criteriaBuilder))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, criteriaBuilder));


        TypedQuery<Tuple> executableQuery = entityManager.createQuery(cq);

        List<Long> result = SpecificationsHelper.setPage(executableQuery, pageable)
                .getResultList().stream()
                .map(tuple -> tuple.get(idField))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public Optional<Long> findFirstId(Specification<T> specification, Sort sort) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = criteriaBuilder.createQuery(Tuple.class);
        Root<T> root = cq.from(entityClass);

        Path<Long> idField = root.get(entityId);

        List<Selection<?>> selectedColumns = SpecificationsHelper.getSortingColumns(root, sort);
        selectedColumns.add(idField);

        cq.multiselect(selectedColumns)
                .where(specification.toPredicate(root, cq, criteriaBuilder))
                .orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));


        TypedQuery<Tuple> executableQuery = entityManager.createQuery(cq);

        List<Long> result = SpecificationsHelper.setPage(executableQuery, PageRequest.of(0, 1))
                .getResultList().stream()
                .map(tuple -> tuple.get(idField))
                .collect(Collectors.toList());

        return result.stream().findFirst();
    }

    @Override
    public List<Long> findAllId(Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        Path<Long> idField = root.get(entityId);

        cq.distinct(true)
                .select(idField)
                .where(specification.toPredicate(root, cq, criteriaBuilder));

        return entityManager.createQuery(cq.select(idField))
                .getResultList();
    }

    @Override
    public boolean exists(Specification<T> specification) {
//        long count = getCount(specification);
//        return count > 0;
        return findFirstId(specification, Sort.unsorted()).isPresent();
    }

   private long getCount(Specification<T> specification) {
       CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

       CriteriaQuery<Long> countQq = criteriaBuilder.createQuery(Long.class);
       Root<T> countRoot = countQq.from(entityClass);

       countQq.select(criteriaBuilder.countDistinct(countRoot))
               .where(specification.toPredicate(countRoot, countQq, criteriaBuilder));
       return entityManager.createQuery(countQq).getSingleResult();
    }
}
