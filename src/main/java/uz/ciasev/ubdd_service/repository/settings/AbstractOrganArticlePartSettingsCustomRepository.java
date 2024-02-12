package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart_;
import uz.ciasev.ubdd_service.entity.settings.*;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public abstract class AbstractOrganArticlePartSettingsCustomRepository<T extends OrganAbstractArticlePartSettings> extends EntityRepositoryImpl<T> implements OrganArticlePartSettingsCustomRepository<T> {

    private final EntityManager entityManager;

    public AbstractOrganArticlePartSettingsCustomRepository(EntityManager entityManager, Class<T> entityClass, SingularAttribute<T, Long> entityId) {
        super(entityManager, entityClass, entityId);
        this.entityManager = entityManager;
    }

    @Override
    public List<Article> findArticle(Specification<T> specification) {
        return findArticle(specification, Sort.unsorted());
    }

    @Override
    public List<Article> findArticle(Specification<T> specification, Sort sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Article> cq = criteriaBuilder.createQuery(Article.class).distinct(true);
        Root<T> root = cq.from(this.entityClass);
        Join<T, ArticlePart> articlePartJoin = root.join(OrganAbstractArticlePartSettings_.articlePart);

        cq.select(articlePartJoin.get(ArticlePart_.article));
        cq.where(specification.toPredicate(root, cq, criteriaBuilder));
        cq.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));

        return entityManager.createQuery(cq)
                .getResultList();
    }

    @Override
    public List<ArticlePart> findArticlePart(Specification<T> specification) {
        return findArticlePart(specification, Sort.unsorted());
    }

    @Override
    public List<ArticlePart> findArticlePart(Specification<T> specification, Sort sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ArticlePart> cq = criteriaBuilder.createQuery(ArticlePart.class).distinct(true);
        Root<T> root = cq.from(this.entityClass);

        cq.select(root.get(OrganAbstractArticlePartSettings_.articlePart));
        cq.where(specification.toPredicate(root, cq, criteriaBuilder));
        cq.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));

        return entityManager.createQuery(cq)
                .getResultList();
    }


    @Override
    public List<OrganArticleSettingsProjection> findOrganArticleSettingsProjection(Specification<T> specification) {
        return findOrganArticleSettingsProjection(specification, Sort.unsorted());
    }

    public List<OrganArticleSettingsProjection> findOrganArticleSettingsProjection(Specification<T> specification, Sort sort) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrganArticleSettingsProjectionBean> cq = criteriaBuilder.createQuery(OrganArticleSettingsProjectionBean.class).distinct(true);
        Root<T> root = cq.from(this.entityClass);
        Join<T, ArticlePart> articlePartJoin = root.join(OrganAbstractArticlePartSettings_.articlePart);

        List<Selection<?>> selectedFields = List.of(
                root.get(this.entityId),
                articlePartJoin.get(ArticlePart_.articleId),
                root.get(OrganAbstractArticlePartSettings_.articlePartId),
                articlePartJoin.get(ArticlePart_.isDiscount),
                articlePartJoin.get(ArticlePart_.isActive)
        );

        cq.multiselect(selectedFields);
        cq.where(specification.toPredicate(root, cq, criteriaBuilder));
        cq.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));

        List<OrganArticleSettingsProjectionBean> res = entityManager.createQuery(cq)
                .getResultList();

        return res.stream()
                .map(e -> (OrganArticleSettingsProjection) e)
                .collect(Collectors.toList());

    }


}
