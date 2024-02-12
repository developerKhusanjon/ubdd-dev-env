package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.dict.mail.MailDeliveryStatus_;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification_;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution_;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class MailNotificationSpecifications {

    public Specification<MailNotification> withMessageNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.messageNumber), value);
        };
    }



    public Specification<MailNotification> withIdIn(List<Long> value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return root.get(MailNotification_.id).in(value);
        };
    }

    public Specification<MailNotification> withDeliveryStatusId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.deliveryStatus).get(MailDeliveryStatus_.id), value);
        };
    }

    public Specification<MailNotification> withNotificationType(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            try {
                return cb.equal(root.get(MailNotification_.notificationTypeAlias), NotificationTypeAlias.valueOf(value));
            } catch (Exception e) {
                return cb.disjunction();
            }

        };
    }

    public Specification<MailNotification> withNotificationTypeId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.notificationTypeId), value);
        };
    }

    public Specification<MailNotification> withSendDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(SpecificationsHelper.toDate(cb, root.get(MailNotification_.sendTime)), value);
        };
    }

    public Specification<MailNotification> withSendDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.lessThanOrEqualTo(SpecificationsHelper.toDate(cb, root.get(MailNotification_.sendTime)), value);
        };
    }

    public Specification<MailNotification> withPerformDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(root.get(MailNotification_.receiveDate), value);
        };
    }

    public Specification<MailNotification> withPerformDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.lessThanOrEqualTo(root.get(MailNotification_.receiveDate), value);
        };
    }

    public Specification<MailNotification> withDecisionNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithDecision(root, query, cb,
                    decisionJoin -> cb.equal(decisionJoin.get(Decision_.number), value)
            );
        };
    }

    public Specification<MailNotification> withResolutionTimeAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<MailNotification> withResolutionTimeBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.lessThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<MailNotification> withDecisionArticlePartIdIn(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithDecision(root, query, cb,
                    join -> cb.equal(join.get(Decision_.articlePartId), value)
            );
        };
    }

    public Specification<MailNotification> withAdmCaseOrganId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.organId), value)
            );
        };
    }

    public Specification<MailNotification> withAdmCaseDepartmentId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.departmentId), value)
            );
        };
    }

    public Specification<MailNotification> withAdmCaseRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.regionId), value)
            );
        };
    }

    public Specification<MailNotification> withAdmCaseDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.districtId), value)
            );
        };
    }

    public Specification<MailNotification> withViolatorFirstName(String value) {
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

    public Specification<MailNotification> withViolatorSecondName(String value) {
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
        };
    }

    public Specification<MailNotification> withViolatorLastName(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.lastNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.lastNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<MailNotification> withViolatorBirthAfter(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.greaterThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<MailNotification> withViolatorBirthBefore(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.lessThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<MailNotification> withViolatorDocumentSeries(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentSeries), value)
            );
        };
    }

    public Specification<MailNotification> withViolatorDocumentNumber(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentNumber), value)
            );
        };
    }

    public Specification<MailNotification> withEntityAlias(EntityNameAlias value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            if (EntityNameAlias.DECISION.equals(value)) {
                return cb.conjunction();
            }

            return cb.not(cb.conjunction());
        };
    }

    public Specification<MailNotification> withId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.id), value);
        };
    }

    public Specification<MailNotification> withEntityId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.decision).get(Decision_.id), value);
        };
    }

    public Specification<MailNotification> withType(NotificationTypeAlias value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(MailNotification_.notificationTypeAlias), value);
        };
    }

    public Specification<MailNotification> withStatusIdIn(Set<Long> values) {
        return (root, query, cb) -> {
            if (values == null) {
                return cb.conjunction();
            }
            if (values.size() == 0) {
                return cb.conjunction();
            }
            return joinWithDecision(root, query, cb,
                    join -> cb.and(join.get(Decision_.statusId).in(values))
            );
//            return joinWithAdmStatus(root, query, cb,
//                    join -> cb.and(join.get(AdmStatus_.id).in(values))
//            );
        };
    }


    // JOINS

    private Predicate joinWithDecision(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<MailNotification, Decision>, Predicate> function) {
        Join<MailNotification, Decision> decisionJoin = SpecificationsHelper.getExistJoin(root, MailNotification_.decision);
        return function.apply(decisionJoin);
    }

    private Predicate joinWithResolution(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Resolution>, Predicate> function) {
        return joinWithDecision(root, query, cb,
                decisionJoin -> {
                    Join<Decision, Resolution> join = SpecificationsHelper.getExistJoin(decisionJoin, Decision_.resolution);
                    return function.apply(join);
                });
    }

    private Predicate joinWithViolator(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Violator>, Predicate> function) {
        return joinWithDecision(root, query, cb, decisionJoin -> {
            Join<Decision, Violator> violatorJoin = SpecificationsHelper.getExistJoin(decisionJoin, Decision_.violator);
            return function.apply(violatorJoin);
        });
    }

    private Predicate joinWithAdmStatus(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, AdmStatus>, Predicate> function) {
        return joinWithDecision(root, query, cb, decisionJoin -> {
            Join<Decision, AdmStatus> statusJoin = SpecificationsHelper.getExistJoin(decisionJoin, Decision_.status);
            return function.apply(statusJoin);
        });
    }

    private Predicate joinWithViolatorPerson(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Person>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Person> personJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.person);
            return function.apply(personJoin);
        });
    }

    private Predicate joinWithViolatorDetail(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, ViolatorDetail>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorDetailJoin -> {
            Join<Violator, ViolatorDetail> join = SpecificationsHelper.getExistJoin(violatorDetailJoin, Violator_.violatorDetails);
            return function.apply(join);
        });
    }

    private Predicate joinWithAdmCase(Root<MailNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Resolution, AdmCase>, Predicate> function) {
        return joinWithResolution(root, query, cb, joinAdm -> {
            Join<Resolution, AdmCase> join = SpecificationsHelper.getExistJoin(joinAdm, Resolution_.admCase);
            return function.apply(join);
        });
    }
}
