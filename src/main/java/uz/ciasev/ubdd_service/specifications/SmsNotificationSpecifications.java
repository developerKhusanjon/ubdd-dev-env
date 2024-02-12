package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification_;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class SmsNotificationSpecifications {

    public Specification<SmsNotification> withEntityId(@Nullable Long value) {
        return new Specification<SmsNotification>() {
            @Override
            public Predicate toPredicate(Root<SmsNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SmsNotification_.entityId), value);
            }
        };
    }

    public Specification<SmsNotification> withEntityAlias(@Nullable EntityNameAlias value) {
        return new Specification<SmsNotification>() {
            @Override
            public Predicate toPredicate(Root<SmsNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SmsNotification_.entityType), value);
            }
        };
    }

    public Specification<SmsNotification> withType(@Nullable NotificationTypeAlias value) {
        return new Specification<SmsNotification>() {
            @Override
            public Predicate toPredicate(Root<SmsNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SmsNotification_.notificationTypeAlias), value);
            }
        };
    }
}
