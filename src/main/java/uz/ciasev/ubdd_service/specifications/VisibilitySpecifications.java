package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VisibilitySpecifications<T> {

    public Specification<T> inUserVisibility(User user) {

        return SpecificationsCombiner.andAll(
                withOrgan(user.getOrgan()),
                withDepartment(user.getDepartment()),
                withRegion(user.getRegion()),
                withDistrict(user.getDistrict())
        );
    }

    public Specification<T> withOrgan(@Nullable Organ organ) {
        Long value = Optional.ofNullable(organ).map(Organ::getId).orElse(null);
        return withOrganId(value);
    }

    public Specification<T> withOrganId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("organId"), value);
        };
    }

    public Specification<T> withDepartment(@Nullable Department department) {
        Long value = Optional.ofNullable(department).map(Department::getId).orElse(null);
        return withDepartmentId(value);
    }

    public Specification<T> withDepartmentId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("departmentId"), value);
        };
    }

    public Specification<T> withRegion(Region region) {
        Long value = Optional.ofNullable(region).map(Region::getId).orElse(null);
        return withRegionId(value);
    }

    public Specification<T> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("regionId"), value);
        };
    }

    public Specification<T> withDistrict(@Nullable District district) {
        Long value = Optional.ofNullable(district).map(District::getId).orElse(null);
        return withDistrictId(value);
    }

    public Specification<T> withDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("districtId"), value);
        };
    }

    public Specification<T> withOrganIn(Collection<Organ> list) {
        List<Long> value = Optional.ofNullable(list)
                .map(l -> l.stream().map(Organ::getId).collect(Collectors.toList()))
                .orElse(null);

        return withOrganIdIn(value);
    }

    public Specification<T> withOrganIdIn(@Nullable Collection<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return root.get("organId").in(value);
        };
    }

    public Specification<T> withDepartmentIn(@Nullable Collection<Department> list) {
        List<Long> value = Optional.ofNullable(list)
                .map(l -> l.stream().map(Department::getId).collect(Collectors.toList()))
                .orElse(null);

        return withDepartmentIdIn(value);
    }

    public Specification<T> withDepartmentIdIn(@Nullable Collection<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return root.get("departmentId").in(value);
        };
    }

    public Specification<T> withRegionIn(Collection<Region> list) {
        List<Long> value = Optional.ofNullable(list)
                .map(l -> l.stream().map(Region::getId).collect(Collectors.toList()))
                .orElse(null);

        return withRegionIdIn(value);
    }

    public Specification<T> withRegionIdIn(Collection<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return root.get("regionId").in(value);
        };
    }

    public Specification<T> withDistrictIn(@Nullable Collection<District> list) {
        List<Long> value = Optional.ofNullable(list)
                .map(l -> l.stream().map(District::getId).collect(Collectors.toList()))
                .orElse(null);

        return withDistrictIdIn(value);
    }

    public Specification<T> withDistrictIdIn(@Nullable Collection<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return root.get("districtId").in(value);
        };
    }

    public Specification<T> withOrganExactly(@Nullable Organ organ) {
        return (root, query, cb) -> {
            Long id = Optional.ofNullable(organ).map(Organ::getId).orElse(null);

            if (id == null) {
                return cb.isNull(root.get("organId"));
            }

            return cb.equal(root.get("organId"), id);
        };
    }

    public Specification<T> withDepartmentExactly(@Nullable Department department) {
        return (root, query, cb) -> {
            Long id = Optional.ofNullable(department).map(Department::getId).orElse(null);

            if (id == null) {
                return cb.isNull(root.get("departmentId"));
            }

            return cb.equal(root.get("departmentId"), id);
        };
    }

    public Specification<T> withRegionExactly(@Nullable Region region) {
        return (root, query, cb) -> {
            Long id = Optional.ofNullable(region).map(Region::getId).orElse(null);

            if (id == null) {
                return cb.isNull(root.get("regionId"));
            }

            return cb.equal(root.get("regionId"), id);
        };
    }

    public Specification<T> withDistrictExactly(@Nullable District district) {
        return (root, query, cb) -> {
            Long id = Optional.ofNullable(district).map(District::getId).orElse(null);

            if (id == null) {
                return cb.isNull(root.get("districtId"));
            }

            return cb.equal(root.get("districtId"), id);
        };
    }

    public Specification<T> withoutOrganId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.notEqual(root.get("organId"), value);
        };
    }

    public Specification<T> withoutDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.notEqual(root.get("districtId"), value);
        };
    }

    public Specification<T> withoutRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.notEqual(root.get("regionId"), value);
        };
    }
}
