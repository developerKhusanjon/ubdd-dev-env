package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.notification.SystemUserNotification;
import uz.ciasev.ubdd_service.entity.notification.SystemUserNotification_;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class SystemUserNotificationSpecifications {

    public Specification<SystemUserNotification> withIsRead(@Nullable Boolean value) {
        return new Specification<SystemUserNotification>() {
            @Override
            public Predicate toPredicate(Root<SystemUserNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SystemUserNotification_.isRead), value);
            }
        };
    }

    public Specification<SystemUserNotification> withNotificationType(@Nullable String value) {
        NotificationTypeAlias castValue = null;
        try {
            castValue = NotificationTypeAlias.valueOf(value);
        } catch (Exception e) {}

        if (castValue == null) {
            return SpecificationsHelper.getEmpty();
        } else {
            return withNotificationType(castValue);
        }
    }

    public Specification<SystemUserNotification> withNotificationType(@Nullable NotificationTypeAlias value) {
        return new Specification<SystemUserNotification>() {
            @Override
            public Predicate toPredicate(Root<SystemUserNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SystemUserNotification_.notificationType), value);
            }
        };
    }

    public Specification<SystemUserNotification> withUserId(@Nullable Long value) {
        return new Specification<SystemUserNotification>() {
            @Override
            public Predicate toPredicate(Root<SystemUserNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(SystemUserNotification_.userId), value);
            }
        };
    }
}
