package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.settings.BankAccount_;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings_;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrganAccountSettingsSpecifications extends VisibilitySpecifications<OrganAccountSettings> {

    public Specification<OrganAccountSettings> withArticlePartId(@Nullable Long value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return SpecificationsHelper.getNullableLongSpecification(root, cb, OrganAccountSettings_.articlePartId, value);
            }
        };
    }

    public Specification<OrganAccountSettings> withArticlePartIn(@Nullable Collection<ArticlePart> list) {
        List<Long> value = Optional.ofNullable(list)
                .map(l -> l.stream().map(ArticlePart::getId).collect(Collectors.toList()))
                .orElse(null);

        return withArticlePartIdIn(value);

    }

    public Specification<OrganAccountSettings> withArticlePartIdIn(@Nullable Collection<Long> value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return root.get(OrganAccountSettings_.articlePartId).in(value);
            }
        };
    }

    public Specification<OrganAccountSettings> withArticlePartExact(@Nullable Collection<Long> value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.isNull(root.get(OrganAccountSettings_.articlePartId));
                }

                return cb.equal(root.get(OrganAccountSettings_.articlePartId), value);
            }
        };
    }

    public Specification<OrganAccountSettings> withBankAccountTypeId(@Nullable Long value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return SpecificationsHelper.getNullableLongSpecification(root, cb, OrganAccountSettings_.bankAccountTypeId, value);
            }
        };
    }

    public Specification<OrganAccountSettings> withPenaltyBillingId(@Nullable Long value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return joinWithPenaltyAccount(root, query, cb,
                        accountJoin -> cb.equal(accountJoin.get(BankAccount_.billingId), value));
            }
        };
    }

    public Specification<OrganAccountSettings> withCompensationBillingId(@Nullable Long value) {
        return new Specification<OrganAccountSettings>() {
            @Override
            public Predicate toPredicate(Root<OrganAccountSettings> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (value == null) {
                    return cb.conjunction();
                }

                return joinWithCompensationAccount(root, query, cb,
                        accountJoin -> cb.equal(accountJoin.get(BankAccount_.billingId), value));
            }
        };
    }

    private Predicate joinWithPenaltyAccount(Root<OrganAccountSettings> root, CriteriaQuery<?>
            query, CriteriaBuilder cb, Function<From<OrganAccountSettings, BankAccount>, Predicate> function) {
        Join<OrganAccountSettings, BankAccount> join = SpecificationsHelper.getExistJoin(root, OrganAccountSettings_.penaltyAccount);
        return function.apply(join);
    }

    private Predicate joinWithCompensationAccount(Root<OrganAccountSettings> root, CriteriaQuery<?>
            query, CriteriaBuilder cb, Function<From<OrganAccountSettings, BankAccount>, Predicate> function) {
        Join<OrganAccountSettings, BankAccount> join = SpecificationsHelper.getExistJoin(root, OrganAccountSettings_.compensationAccount);
        return function.apply(join);
    }
}
