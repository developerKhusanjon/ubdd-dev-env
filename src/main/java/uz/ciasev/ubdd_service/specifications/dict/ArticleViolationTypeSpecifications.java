package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.util.function.Function;


@Component
public class ArticleViolationTypeSpecifications {

    public Specification<ArticleViolationType> withTagId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithTagId(root, query, cb,
                    join -> cb.equal(join.get(ArticleViolationTypeViolationTypeTag_.articleViolationTypeTagId), value)
            );
        };
    }

    private Predicate joinWithTagId(
            Root<ArticleViolationType> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb,
            Function<
                    From<ArticleViolationType, ArticleViolationTypeViolationTypeTag>,
                    Predicate
                    > function) {

        Join<ArticleViolationType, ArticleViolationTypeViolationTypeTag> articleTagJoin = SpecificationsHelper.getExistJoin(root, ArticleViolationType_.articleViolationTypeViolationTypeTags);

        return function.apply(articleTagJoin);
    }
}