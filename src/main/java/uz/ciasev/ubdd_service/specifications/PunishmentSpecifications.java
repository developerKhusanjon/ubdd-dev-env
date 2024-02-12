package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.resolution.punishment.LicenseRevocationPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.LicenseRevocationPunishment_;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment_;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.function.Function;

@Component
public class PunishmentSpecifications {

    public Specification<Punishment> withLicenseRevocationMayBeReturnedDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithLicenseRevocation(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(
                            join.get(LicenseRevocationPunishment_.mayBeReturnedAfterDate),
                            value
                    )
            );
        };
    }

    public Specification<Punishment> withLicenseRevocationMayBeReturnedDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithLicenseRevocation(root, query, cb,
                    join -> cb.lessThanOrEqualTo(
                            join.get(LicenseRevocationPunishment_.mayBeReturnedAfterDate),
                            value
                    )
            );
        };
    }

    public Specification<Punishment> withStatusAlias(AdmStatusAlias... values) {
        return (root, query, cb) -> {
            if (values.length == 0) {
                return cb.conjunction();
            }

            return root.get(Punishment_.statusId).in(AdmStatusAlias.getIds(values));
//            return root.get(Punishment_.status).get(AdmStatus_.alias).in(values);
        };
    }

    public Specification<Punishment> withStatusAlias(AdmStatusAlias value) {
        if (value == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return withStatusAlias(value);
    }

    private Predicate joinWithLicenseRevocation(Root<Punishment> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Punishment, LicenseRevocationPunishment>, Predicate> function) {
        Join<Punishment, LicenseRevocationPunishment> join = SpecificationsHelper.getExistJoin(root, Punishment_.licenseRevocation);
        return function.apply(join);
    }
}
