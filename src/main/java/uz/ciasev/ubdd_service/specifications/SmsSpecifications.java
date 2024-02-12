package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification_;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.function.Function;

@Component
public class SmsSpecifications {

    public Specification<SmsNotification> withViolatorFirstNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.firstNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.firstNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<SmsNotification> withViolatorSecondNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.secondNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.secondNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<SmsNotification> withViolatorLastNameLike(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithPerson(root, query, cb,
                    join -> cb.or(
                            cb.like(join.get(Person_.lastNameKir), likeValue),
                            cb.like(join.get(Person_.lastNameLat), likeValue)
                    )
            );
        };
    }

    public Specification<SmsNotification> withViolatorBirthDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPerson(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<SmsNotification> withViolatorBirthDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPerson(root, query, cb,
                    join -> cb.lessThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<SmsNotification> withPhoneNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, SmsNotification_.phoneNumber, value);
    }

    public Specification<SmsNotification> withNotificationType(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            NotificationTypeAlias notificationTypeAlias;
            try {
                notificationTypeAlias = NotificationTypeAlias.valueOf(value);
            } catch (Exception e) {
                return cb.conjunction();
            }
            return cb.equal(root.get(SmsNotification_.notificationTypeAlias), notificationTypeAlias);
        };
    }

    public Specification<SmsNotification> withDeliveryStatusId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, SmsNotification_.deliveryStatusId, value);
    }

    public Specification<SmsNotification> withMessageId(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, SmsNotification_.messageId, value);
    }

    public Specification<SmsNotification> withSendDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, SmsNotification_.sendTime, value);
    }

    public Specification<SmsNotification> withSendDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, SmsNotification_.sendTime, value);
    }

    public Specification<SmsNotification> withReceiveDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, SmsNotification_.receiveTime, value);
    }

    public Specification<SmsNotification> withReceiveDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, SmsNotification_.receiveTime, value);
    }

    public Specification<SmsNotification> withOrganId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.organId), value)
            );
        };
    }

    public Specification<SmsNotification> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.regionId), value)
            );
        };
    }

    public Specification<SmsNotification> withDistrictId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.districtId), value)
            );
        };
    }


    // JOINS

    private Predicate joinWithPerson(Root<SmsNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                             Function<From<SmsNotification, Person>, Predicate> function) {

        Join<SmsNotification, Person> join = SpecificationsHelper.getExistJoin(root, SmsNotification_.person);
        return function.apply(join);
    }

    private Predicate joinWithAdmCase(Root<SmsNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                      Function<From<SmsNotification, AdmCase>, Predicate> function) {

        Join<SmsNotification, AdmCase> join = SpecificationsHelper.getExistJoin(root, SmsNotification_.admCase);
        return function.apply(join);
    }
}
