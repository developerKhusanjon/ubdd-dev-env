package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.util.function.Function;


@Component
public class ArticlePartSpecifications {

    public Specification<ArticlePart> withId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(ArticlePart_.id), value);
        };
    }

    public Specification<ArticlePart> withNumber(@Nullable Integer value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableIntSpecification(root, cb, ArticlePart_.number, value);
    }

    public Specification<ArticlePart> withArticle(@Nullable Article value) {
        if (value == null) return SpecificationsHelper.getEmpty();
        return withArticleId(value.getId());
    }

    public Specification<ArticlePart> withArticleId(@Nullable Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, ArticlePart_.articleId, value);
    }

    public Specification<ArticlePart> withTagId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithTagId(root, query, cb,
                    join -> cb.equal(join.get(ArticlePartArticleTag_.articleTagId), value)
            );
        };
    }

    private Predicate joinWithTagId(
            Root<ArticlePart> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb,
            Function<
                    From<ArticlePart, ArticlePartArticleTag>,
                    Predicate
                    > function) {

        Join<ArticlePart, ArticlePartArticleTag> articleTagJoin = SpecificationsHelper.getExistJoin(root, ArticlePart_.articlePartArticleTags);

        return function.apply(articleTagJoin);
    }
}