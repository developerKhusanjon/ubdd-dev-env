package uz.ciasev.ubdd_service.specifications.dict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.District_;

@Component
public class DistrictSpecifications {

    public static Specification<District> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(District_.regionId), value);
        };
    }
}