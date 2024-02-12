package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution_;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution_;

import javax.persistence.criteria.*;
import java.util.function.Function;

@Component
public class CancellationResolutionSpecifications {

    public Specification<CancellationResolution> withAdmCaseId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.equal(join.get(Resolution_.admCaseId), value)
            );
        };
    }

    public Specification<CancellationResolution> withResolutionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(CancellationResolution_.resolutionId), value);
        };
    }

    // JOINS

    private Predicate joinWithResolution(Root<CancellationResolution> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<CancellationResolution, Resolution>, Predicate> function) {
        Join<CancellationResolution, Resolution> join = SpecificationsHelper.getExistJoin(root, CancellationResolution_.resolution);
        return function.apply(join);
    }

    private Predicate joinWithAdmCase(Root<CancellationResolution> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Resolution, AdmCase>, Predicate> function) {
        return joinWithResolution(root, query, cb, joinAdm -> {
            Join<Resolution, AdmCase> join = SpecificationsHelper.getExistJoin(joinAdm, Resolution_.admCase);
            return function.apply(join);
        });
    }
}
