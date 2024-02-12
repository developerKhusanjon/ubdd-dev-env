package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.annotation.Nullable;
import javax.persistence.criteria.Expression;

public class DictionarySpecifications {

    public static <T extends AbstractDict> Specification<T> withIsActive(@Nullable Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(AbstractDict_.isActive), value);
        };
    }

    public static <T extends AbstractDict> Specification<T> withNameLike(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            Expression<String> nameCastedToText = cb.function(
                    "text",
                    String.class,
                    root.get(AbstractDict_.name)
            );

            return cb.like(nameCastedToText, SpecificationsHelper.likeValue(value));
        };
    }

    public static <T extends AbstractDict> Specification<T> withCode(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(AbstractDict_.code), value);
        };
    }

    public static <T extends AbstractDict> Specification<T> withId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("id"), value);
        };
    }

    public static Specification<Bank> withMfo(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Bank_.mfo), value);
        };
    }

    public static Specification<District> withRegionId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(District_.regionId), value);
        };
    }

    public static Specification<FileFormat> withExtension(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(FileFormat_.extension), value.toUpperCase());
        };
    }

    public static Specification<Mtp> withDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Mtp_.districtId), value);
        };
    }
}
