package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.util.function.Function;

@Component
public class MtpSpecifications {

    public Specification<Mtp> withDistrictId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Mtp_.districtId), value);
        };
    }

    public Specification<Mtp> withRegionId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithDistrict(root, query, cb,
                districtFrom -> cb.equal(districtFrom.get(District_.regionId), value)
                );
            };
    }

    private Predicate joinWithDistrict(Root<Mtp> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                       Function<From<Mtp, District>, Predicate> function) {
        Join<Mtp, District> districtJoin = SpecificationsHelper.getExistJoin(root, Mtp_.district);
        return function.apply(districtJoin);
    }
}
