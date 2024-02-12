package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate_;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserRole;
import uz.ciasev.ubdd_service.entity.user.UserRole_;
import uz.ciasev.ubdd_service.entity.user.User_;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;

@Component
public class UserSpecifications extends VisibilitySpecifications<User> {

    public Specification<User> withUsernameLike(@Nullable String value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null || value.isBlank()) {
                    return cb.conjunction();
                }

                return cb.like(root.get(User_.username), SpecificationsHelper.likeValue(value));
            }
        };
    }

    public Specification<User> withWorkCertificateLike(@Nullable String value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null || value.isBlank()) {
                    return cb.conjunction();
                }

                return cb.like(root.get(User_.workCertificate), SpecificationsHelper.likeValue(value));
            }
        };
    }

    public Specification<User> withIsActive(@Nullable Boolean value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(User_.isActive), value);
            }
        };
    }

    public Specification<User> withIsSystemNotificationSubscriber(@Nullable Boolean value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(User_.isSystemNotificationSubscriber), value);
            }
        };
    }

    public Specification<User> withIsConsider(@Nullable Boolean value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(User_.isConsider), value);
            }
        };
    }

    public Specification<User> withIsExternal(@Nullable Boolean value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(User_.isExternal), value);
            }
        };
    }

    public Specification<User> withIsOffline(@Nullable Boolean value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return cb.equal(root.get(User_.isOffline), value);
            }
        };
    }

    public Specification<User> withFIOLike(@Nullable String value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null || value.isBlank()) {
                    return cb.conjunction();
                }

                String likeValue = SpecificationsHelper.likeValue(value);

                Path<String> firstNameKir = root.get(User_.person).get(Person_.firstNameKir);
                Path<String> secondNameKir = root.get(User_.person).get(Person_.secondNameKir);
                Path<String> lastNameKir = root.get(User_.person).get(Person_.lastNameKir);

                Path<String> firstNameLat = root.get(User_.person).get(Person_.firstNameLat);
                Path<String> secondNameLat = root.get(User_.person).get(Person_.secondNameLat);
                Path<String> lastNameLat = root.get(User_.person).get(Person_.lastNameLat);

                return cb.or(
                        cb.like(firstNameKir, likeValue),
                        cb.like(secondNameKir, likeValue),
                        cb.like(lastNameKir, likeValue),
                        cb.like(firstNameLat, likeValue),
                        cb.like(secondNameLat, likeValue),
                        cb.like(lastNameLat, likeValue)
                );
            }
        };
    }

    public Specification<User> withRole(@Nullable Long value) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (value == null) {
                    return builder.conjunction();
                }

                Subquery<UserRole> subquery = query.subquery(UserRole.class);
                Root<UserRole> subqueryRoot = subquery.from(UserRole.class);
                subquery.select(subqueryRoot);

                subquery.where(
                        builder.and(
                                builder.equal(root.get(User_.id), subqueryRoot.get(UserRole_.userId)),
                                subqueryRoot.get(UserRole_.roleId).in(value)
                        )
                );

                return builder.exists(subquery);
            }
        };
    }

    public Specification<User> withSignatureCertificateActive(@Nullable Boolean value) {
        if (value == null) {
            return SpecificationsHelper.getEmpty();
        }

        Specification<User> spec = withActiveSignatureCertificate();

        if (value == true) {
            return spec;
        }

        return Specification.not(spec);
    }

    public Specification<User> withActiveSignatureCertificate() {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                Subquery<DigitalSignatureCertificate> subquery = query.subquery(DigitalSignatureCertificate.class);
                Root<DigitalSignatureCertificate> subqueryRoot = subquery.from(DigitalSignatureCertificate.class);
                subquery.select(subqueryRoot);

                subquery.where(
                        builder.and(
                                builder.equal(root.get(User_.id), subqueryRoot.get(DigitalSignatureCertificate_.userId)),
                                builder.isTrue(subqueryRoot.get(DigitalSignatureCertificate_.isActive))
                        )
                );

                return builder.exists(subquery);
            }
        };
    }
}
