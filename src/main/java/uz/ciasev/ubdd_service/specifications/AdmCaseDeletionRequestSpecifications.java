package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.function.Function;

@Component
public class AdmCaseDeletionRequestSpecifications extends VisibilitySpecifications<AdmCaseDeletionRequest> {

    public Specification<AdmCaseDeletionRequest> withStatusId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(AdmCaseDeletionRequest_.statusId), value);
        };
    }

    public Specification<AdmCaseDeletionRequest> withDeleteReasonId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(AdmCaseDeletionRequest_.deleteReasonId), value);
        };
    }

    public Specification<AdmCaseDeletionRequest> withUserId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(AdmCaseDeletionRequest_.userId), value);
        };
    }

    public Specification<AdmCaseDeletionRequest> withAdminId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(AdmCaseDeletionRequest_.adminId), value);
        };
    }

    @Override
    public Specification<AdmCaseDeletionRequest> withOrganId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    organFrom -> cb.equal(organFrom.get(AdmCase_.organId), value)
            );
        };
    }

    @Override
    public Specification<AdmCaseDeletionRequest> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    organFrom -> cb.equal(organFrom.get(AdmCase_.regionId), value)
            );
        };
    }

    @Override
    public Specification<AdmCaseDeletionRequest> withDistrictId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    organFrom -> cb.equal(organFrom.get(AdmCase_.districtId), value)
            );
        };
    }

    @Override
    public Specification<AdmCaseDeletionRequest> withDepartmentId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    organFrom -> cb.equal(organFrom.get(AdmCase_.departmentId), value)
            );
        };
    }

    public Specification<AdmCaseDeletionRequest> withAdmCaseNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    organFrom -> cb.equal(organFrom.get(AdmCase_.number), value)
            );
        };
    }

    public Specification<AdmCaseDeletionRequest> withAdmCaseId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    admCaseIdFrom -> cb.equal(admCaseIdFrom.get(AdmCase_.id), value)
            );
        };
    }

    public Specification<AdmCaseDeletionRequest> createdBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, AdmCaseDeletionRequest_.createdTime, value);
    }

    public Specification<AdmCaseDeletionRequest> createdAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, AdmCaseDeletionRequest_.createdTime, value);
    }

    public Specification<AdmCaseDeletionRequest> editedBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, AdmCaseDeletionRequest_.editedTime, value);
    }

    public Specification<AdmCaseDeletionRequest> editedAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, AdmCaseDeletionRequest_.editedTime, value);
    }

    private Predicate joinWithAdmCase(Root<AdmCaseDeletionRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                      Function<From<AdmCaseDeletionRequest, AdmCase>, Predicate> function) {
        Join<AdmCaseDeletionRequest, AdmCase> admCaseJoin = SpecificationsHelper.getExistJoin(root, AdmCaseDeletionRequest_.admCase);
        return function.apply(admCaseJoin);
    }
}
