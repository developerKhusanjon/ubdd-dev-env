package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus_;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement_;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard_;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.function.Function;

@Component
public class MibMovementSpecifications {

    public Specification<MibCardMovement> withCardId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(MibCardMovement_.cardId), value);

        };
    }

    public Specification<MibCardMovement> createdAfter(MibCardMovement movement) {
        return (root, query, cb) -> {
            if (movement == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get(MibCardMovement_.id), movement.getId());

        };
    }

    public Specification<MibCardMovement> withIsActive(Boolean value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableBoolSpecification(root, cb, MibCardMovement_.isActive, value);
    }

    public Specification<MibCardMovement> withMibRequestId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, MibCardMovement_.mibRequestId, value);
    }

    public Specification<MibCardMovement> withMibCaseNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, MibCardMovement_.mibCaseNumber, value);
    }

    public Specification<MibCardMovement> withSendTimeAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, MibCardMovement_.sendTime, value);
    }

    public Specification<MibCardMovement> withSendTimeBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, MibCardMovement_.sendTime, value);
    }

    public Specification<MibCardMovement> withAcceptTimeAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, MibCardMovement_.acceptTime, value);
    }

    public Specification<MibCardMovement> withAcceptTimeBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, MibCardMovement_.acceptTime, value);
    }

    public Specification<MibCardMovement> withReturnTimeAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, MibCardMovement_.returnTime, value);
    }

    public Specification<MibCardMovement> withReturnTimeBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, MibCardMovement_.returnTime, value);
    }

    public Specification<MibCardMovement> withMibCaseStatusId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MibCardMovement_.mibCaseStatus), value);
        };
    }

    public Specification<MibCardMovement> withMibSendStatusId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MibCardMovement_.sendStatusId), value);
        };
    }

    public Specification<MibCardMovement> withMibCaseStatusTypeId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinMibCaseStatus(root, query, cb,
                    join -> cb.equal(join.get(MibCaseStatus_.typeId), value)
            );

        };
    }

    public Specification<MibCardMovement> withDecisionSpec(Specification<Decision> decisionSpec) {
        return (root, query, cb) -> joinMibCard(root, query, cb, join -> {
            Root<Decision> rootDecision = query.from(Decision.class);

            return cb.and(
                    cb.equal(join.get(MibExecutionCard_.decisionId), rootDecision.get(Decision_.id)),
                    decisionSpec.toPredicate(rootDecision, query, cb)
            );
        });
    }

    private Predicate joinMibCard(Root<MibCardMovement> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<MibCardMovement, MibExecutionCard>, Predicate> function) {
        Join<MibCardMovement, MibExecutionCard> join = SpecificationsHelper.getExistJoin(root, MibCardMovement_.card);
        return function.apply(join);
    }

    private Predicate joinMibCaseStatus(Root<MibCardMovement> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<MibCardMovement, MibCaseStatus>, Predicate> function) {
        Join<MibCardMovement, MibCaseStatus> join = SpecificationsHelper.getExistJoin(root, MibCardMovement_.mibCaseStatus);
        return function.apply(join);
    }
}
