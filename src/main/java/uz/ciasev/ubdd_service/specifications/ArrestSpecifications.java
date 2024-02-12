package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment_;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment_;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;

@Component
public class ArrestSpecifications {

    public Specification<ArrestPunishment> withViolatorFirstNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.firstNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.firstNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<ArrestPunishment> withViolatorSecondNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.secondNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.secondNameLat), likeValue)
                    )
            );
        };    }

    public Specification<ArrestPunishment> withViolatorLastNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.or(
                            cb.like(join.get(Person_.lastNameKir), likeValue),
                            cb.like(join.get(Person_.lastNameLat), likeValue)
                    )
            );
        };    }

    public Specification<ArrestPunishment> withViolatorBirthDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<ArrestPunishment> withViolatorBirthDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.lessThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<ArrestPunishment> withViolatorDocumentNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorDetail(root, query, cb,
                    join -> cb.equal(join.get(ViolatorDetail_.documentNumber), value)
            );
        };
    }

    public Specification<ArrestPunishment> withViolatorDocumentSeries(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorDetail(root, query, cb,
                    join -> cb.equal(join.get(ViolatorDetail_.documentSeries), value)
            );
        };
    }

    public Specification<ArrestPunishment> withDecisionNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithDecision(root, query, cb,
                    join -> cb.equal(join.get(Decision_.number), value)
            );
        };
    }

    public Specification<ArrestPunishment> withDecisionSeries(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return joinWithDecision(root, query, cb,
                    join -> cb.equal(join.get(Decision_.series), value)
            );
        };
    }

    public Specification<ArrestPunishment> withResolutionTimeAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<ArrestPunishment> withResolutionTimeBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.lessThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<ArrestPunishment> withResolutionIsActive() {
        return (root, query, cb) -> {
            return joinWithResolution(root, query, cb,
                    join -> cb.isTrue(join.get(Resolution_.isActive))
            );
        };
    }

    public Specification<ArrestPunishment> withUserVisibility(User user) {
        return withRegionId(user.getRegionId()).and(withDistrictId(user.getDistrictId()));
    }

    public Specification<ArrestPunishment> withArrestPunishmentInDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateGreatSpecification(root, cb, ArrestPunishment_.inDate, value);
    }

    public Specification<ArrestPunishment> withArrestPunishmentInDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateLessSpecification(root, cb, ArrestPunishment_.inDate, value);
    }

    public Specification<ArrestPunishment> withArrestPunishmentOutDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateGreatSpecification(root, cb, ArrestPunishment_.outDate, value);
    }

    public Specification<ArrestPunishment> withArrestPunishmentOutDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateLessSpecification(root, cb, ArrestPunishment_.outDate, value);
    }

    public Specification<ArrestPunishment> withStatusIdIn(Set<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPunishment(root, query, cb,
                    join -> cb.and(join.get(Punishment_.statusId).in(value))
            );
        };
    }

    public Specification<ArrestPunishment> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.regionId), value)
            );
        };
    }

    public Specification<ArrestPunishment> withDistrictId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.districtId), value)
            );
        };
    }


    // JOINS

    private Predicate joinWithPunishment(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                         Function<From<ArrestPunishment, Punishment>, Predicate> function) {

        Join<ArrestPunishment, Punishment> join = SpecificationsHelper.getExistJoin(root, ArrestPunishment_.punishment);
        return function.apply(join);
    }

    private Predicate joinWithDecision(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                         Function<From<Punishment, Decision>, Predicate> function) {

        return joinWithPunishment(root, query, cb, punishmentJoin -> {
            Join<Punishment, Decision> join = SpecificationsHelper.getExistJoin(punishmentJoin, Punishment_.decision);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolator(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                       Function<From<Decision, Violator>, Predicate> function) {

        return joinWithDecision(root, query, cb, decisionJoin -> {
            Join<Decision, Violator> join = SpecificationsHelper.getExistJoin(decisionJoin, Decision_.violator);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolatorPerson(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                             Function<From<Violator, Person>, Predicate> function) {

        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Person> join = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.person);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolatorDetail(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                             Function<From<Violator, ViolatorDetail>, Predicate> function) {

        return joinWithViolator(root, query, cb, violatorDetailJoin -> {
            Join<Violator, ViolatorDetail> join = SpecificationsHelper.getExistJoin(violatorDetailJoin, Violator_.violatorDetails);
            return function.apply(join);
        });
    }

    private Predicate joinWithResolution(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                       Function<From<Decision, Resolution>, Predicate> function) {

        return joinWithDecision(root, query, cb, decisionJoin -> {
            Join<Decision, Resolution> join = SpecificationsHelper.getExistJoin(decisionJoin, Decision_.resolution);
            return function.apply(join);
        });
    }

    private Predicate joinWithAdmCase(Root<ArrestPunishment> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                      Function<From<Resolution, AdmCase>, Predicate> function) {

        return joinWithResolution(root, query, cb, joinAdm -> {
            Join<Resolution, AdmCase> join = SpecificationsHelper.getExistJoin(joinAdm, Resolution_.admCase);
            return function.apply(join);
        });
    }
}
