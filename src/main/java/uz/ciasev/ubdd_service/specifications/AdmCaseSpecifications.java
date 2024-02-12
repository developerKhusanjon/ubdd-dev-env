package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields_;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.Protocol_;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.function.Function;

@Component
public class AdmCaseSpecifications extends VisibilitySpecifications<AdmCase> {

    public Specification<AdmCase> isActiveOnly() {
        return withIsDeleted(false)
                .and(notArchived());
    }

    public Specification<AdmCase> withIsDeleted(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(AdmCase_.isDeleted), value);
        };
    }

    public Specification<AdmCase> notArchived() {
        return withIsArchived(false);
    }

    public Specification<AdmCase> withIsArchived(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolator(
                    root, query, cb,
                    violatorJoin -> cb.equal(violatorJoin.get(Violator_.isArchived), value)
            );
        };
    }

    public Specification<AdmCase> withConsiderUser(User user) {
        return (root, query, cb) -> cb.equal(root.get(AdmCase_.considerUserId), user.getId());
    }

    public Specification<AdmCase> withStatusIdIn(Collection<Long> statuses) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongInSpecification(root, cb, AdmCase_.statusId, statuses);
    }

    public Specification<AdmCase> withId(Long id) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, AdmCase_.id, id);
    }

    public Specification<AdmCase> withClaimId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, AdmCase_.claimId, value);
    }

    public Specification<AdmCase> withNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, AdmCase_.number, value);
    }

    public Specification<AdmCase> withIdOrNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.or(
                    cb.equal(root.get(AdmCase_.id), value),
                    cb.equal(root.get(AdmCase_.number), value)
            );
        };
    }

    public Specification<AdmCase> createdAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, AdmCase_.createdTime, value);
    }

    public Specification<AdmCase> createdBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, AdmCase_.createdTime, value);
    }

    public Specification<AdmCase> withCourtStatusId(Long courtStatus) {
        return (root, query, cb) -> {
            if (courtStatus == null) {
                return cb.conjunction();
            }

            return joinWithCourtFields(
                    root, query, cb,
                    caseFields -> cb.equal(caseFields.get(CourtCaseFields_.statusId), courtStatus)
            );
        };
    }

    public Specification<AdmCase> withCourtRegionId(Long courtRegionId) {
        return (root, query, cb) -> {
            if (courtRegionId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(AdmCase_.courtRegionId), courtRegionId);
        };
    }

    public Specification<AdmCase> withCourtDistrictId(Long courtDistrictId) {
        return (root, query, cb) -> {
            if (courtDistrictId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(AdmCase_.courtDistrictId), courtDistrictId);
        };
    }

    public Specification<AdmCase> hearingInCourtAfter(LocalDate date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }

            return joinWithCourtFields(
                    root, query, cb,
                    caseFields -> cb.greaterThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, caseFields.get(CourtCaseFields_.hearingDate)),
                            date
                    )
            );
        };
    }

    public Specification<AdmCase> hearingInCourtBefore(LocalDate date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }

            return joinWithCourtFields(
                    root, query, cb,
                    caseFields -> cb.lessThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, caseFields.get(CourtCaseFields_.hearingDate)),
                            date
                    )
            );
        };
    }

    public Specification<AdmCase> violatorPersonFirstNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(
                    root, query, cb,
                    violatorPersonJoin -> cb.or(
                            cb.like(violatorPersonJoin.get(Person_.firstNameKir), likeValue),
                            cb.like(violatorPersonJoin.get(Person_.firstNameLat), likeValue)
                    )
            );

        };
    }

    public Specification<AdmCase> violatorPersonSecondNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(
                    root, query, cb,
                    violatorPersonJoin -> cb.or(
                            cb.like(violatorPersonJoin.get(Person_.secondNameKir), likeValue),
                            cb.like(violatorPersonJoin.get(Person_.secondNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<AdmCase> violatorPersonLastNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(
                    root, query, cb,
                    violatorPersonJoin -> cb.or(
                            cb.like(violatorPersonJoin.get(Person_.lastNameKir), likeValue),
                            cb.like(violatorPersonJoin.get(Person_.lastNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<AdmCase> withProtocolArticlePartId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(
                    root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.articlePartId), value)
            );
        };
    }

    public Specification<AdmCase> withProtocolArticlePartIdIn(Collection<Long> value) {
        return (root, query, cb) -> {
            if (value == null || value.isEmpty()) {
                return cb.conjunction();
            }

            return joinWithProtocol(
                    root, query, cb,
                    protocolFrom -> protocolFrom.get(Protocol_.articlePartId).in(value)
            );
        };
    }

    private Predicate joinWithCourtFields(Root<AdmCase> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<AdmCase, CourtCaseFields>, Predicate> function) {
        Join<AdmCase, CourtCaseFields> courtCaseFieldsJoin = SpecificationsHelper.getExistJoin(root, AdmCase_.courtCaseFields);
        return function.apply(courtCaseFieldsJoin);
    }

    private Predicate joinWithViolator(Root<AdmCase> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<AdmCase, Violator>, Predicate> function) {
        Join<AdmCase, Violator> violatorJoin = SpecificationsHelper.getExistJoin(root, AdmCase_.violators);
        return function.apply(violatorJoin);
    }

    private Predicate joinWithViolatorPerson(Root<AdmCase> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Person>, Predicate> function) {
        return joinWithViolator(
                root, query, cb,
                violatorJoin -> {
                    Join<Violator, Person> personJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.person);
                    return function.apply(personJoin);
                });
    }

    private Predicate joinWithViolatorDetail(Root<AdmCase> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, ViolatorDetail>, Predicate> function) {
        return joinWithViolator(
                root, query, cb,
                violatorJoin -> {
                    Join<Violator, ViolatorDetail> violatorDetailJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.violatorDetails);
                    return function.apply(violatorDetailJoin);
                });
    }

    private Predicate joinWithProtocol(Root<AdmCase> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<ViolatorDetail, Protocol>, Predicate> function) {
        return joinWithViolatorDetail(
                root, query, cb,
                violatorDetailJoin -> {
                    Join<ViolatorDetail, Protocol> protocolJoin = SpecificationsHelper.getExistJoin(violatorDetailJoin, ViolatorDetail_.protocols);
                    return function.apply(protocolJoin);
                });
    }

}
