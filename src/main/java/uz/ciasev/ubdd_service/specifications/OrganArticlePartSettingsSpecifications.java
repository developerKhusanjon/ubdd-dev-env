package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAbstractArticlePartSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganAbstractArticlePartSettings_;

import javax.annotation.Nullable;
import javax.persistence.criteria.Path;
import java.util.Optional;

public abstract class OrganArticlePartSettingsSpecifications<T extends OrganAbstractArticlePartSettings> {


    public Specification<T> withOrganExactly(@Nullable Organ organ) {
        Long id = Optional.ofNullable(organ).map(Organ::getId).orElse(null);
        return withOrganExactly(id);
    }


    public Specification<T> withOrganExactly(@Nullable Long id) {
        return (root, query, cb) -> {
            Path<Long> field = root.get(OrganAbstractArticlePartSettings_.organId);

            if (id == null) {
                return cb.isNull(field);
            }

            return cb.equal(field, id);
        };
    }

    public Specification<T> withDepartmentExactly(@Nullable Department department) {
        Long id = Optional.ofNullable(department).map(Department::getId).orElse(null);
        return withDepartmentExactly(id);
    }

    public Specification<T> withDepartmentExactly(@Nullable Long id) {
        return (root, query, cb) -> {
            Path<Long> field = root.get(OrganAbstractArticlePartSettings_.departmentId);

            if (id == null) {
                return cb.isNull(field);
            }

            return cb.equal(field, id);
        };
    }

    public Specification<T> withArticlePartExactly(ArticlePart articlePart) {
        Long id = Optional.ofNullable(articlePart).map(ArticlePart::getId).orElse(null);
        return withArticlePartExactly(id);
    }

    public Specification<T> withArticlePartExactly(Long id) {
        return (root, query, cb) -> {
            Path<Long> field = root.get(OrganAbstractArticlePartSettings_.articlePartId);

            if (id == null) {
                return cb.isNull(field);
            }

            return cb.equal(field, id);
        };
    }
}
