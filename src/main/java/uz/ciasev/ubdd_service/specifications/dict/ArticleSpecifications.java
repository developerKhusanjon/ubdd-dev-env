package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.annotation.Nullable;


@Component
public class ArticleSpecifications {

    public Specification<Article> withId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Article_.id), value);
        };
    }

    public Specification<Article> withNumber(@Nullable Integer value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableIntSpecification(root, cb, Article_.number, value);
    }

    public Specification<Article> withPrim(@Nullable Integer value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableIntSpecification(root, cb, Article_.prim, value);
    }
}