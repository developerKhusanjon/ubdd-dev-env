package uz.ciasev.ubdd_service.specifications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.protocol.Protocol_;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.service.execution.BillingEntityService;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ViolatorSpecifications {

    private final BillingEntityService billingEntityService;
    private final InvoiceRepository invoiceRepository;

    public Specification<Violator> withDecisionSpec(Specification<Decision> decisionSpec) {
        return (root, query, cb) -> {
            Root<Decision> rootDecision = query.from(Decision.class);

            return cb.and(
                    cb.equal(root.get(Violator_.id), rootDecision.get(Decision_.violatorId)),
                    decisionSpec.toPredicate(rootDecision, query, cb)
            );
        };
    }

    public Specification<Violator> activeOnly() {
        return withIsDeleted(false)
                .and(withIsArchived(false));
    }

    public Specification<Violator> withIsDeleted(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithAdmCase(root, query, cb,
                    admCaseFrom -> cb.equal(admCaseFrom.get(AdmCase_.isDeleted), value)
            );
        };
    }

    public Specification<Violator> withIsArchived(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Violator_.isArchived), value);
        };
    }

    public Specification<Violator> withInvoiceSerial(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            Optional<Invoice> invoiceOpt = invoiceRepository.findByInvoiceSerial(value);
            if (invoiceOpt.isEmpty()) {
                return cb.not(cb.conjunction());
            }

            Violator violator = billingEntityService.getOwnerViolator(invoiceOpt.get());

            return cb.equal(
                    root.get(Violator_.id),
                    violator.getId()
            );
        };
    }

    public Specification<Violator> withDecisionSeriesAndNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithAllDecision(root, query, cb,
                    protocolFrom -> cb.equal(
                            cb.concat(
                                    protocolFrom.get(Decision_.series),
                                    protocolFrom.get(Decision_.number)
                            ),
                            value
                    )
            );
        };
    }

    public Specification<Violator> withProtocolNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.number), value)
            );
        };
    }

    public Specification<Violator> withProtocolSeries(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.series), value)
            );
        };
    }

    public Specification<Violator> withProtocolSeriesAndNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(
                            cb.concat(protocolFrom.get(Protocol_.series), protocolFrom.get(Protocol_.number)),
                            value
                    )
            );
        };
    }

    public Specification<Violator> withProtocolRegisteredAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.greaterThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, protocolFrom.get(Protocol_.registrationTime)),
                            value
                    )
            );
        };
    }

    public Specification<Violator> withProtocolRegisteredBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.lessThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, protocolFrom.get(Protocol_.registrationTime)),
                            value
                    )
            );
        };
    }

    public Specification<Violator> withProtocolArticleId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.articleId), value)
            );
        };
    }

    public Specification<Violator> withProtocolArticleViolationTypeId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.articleViolationTypeId), value)
            );
        };
    }

    public Specification<Violator> withProtocolArticlePartId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithProtocol(root, query, cb,
                    protocolFrom -> cb.equal(protocolFrom.get(Protocol_.articlePartId), value)
            );
        };
    }

    public Specification<Violator> withDocumentTypeId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.personDocumentTypeId), value)
            );
        };
    }

    public Specification<Violator> withProtocolUbddVehicleNumberLike(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.likeValue(value);

            return joinWithProtocol(root, query, cb,
                    join -> cb.like(join.get(Protocol_.vehicleNumber), likeValue)
            );
        };
    }

    public Specification<Violator> withDocumentSeries(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentSeries), value)
            );
        };
    }

    public Specification<Violator> withDocumentNumber(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentNumber), value)
            );
        };
    }

    public Specification<Violator> withDocumentSeriesAndNumber(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(
                            cb.concat(violatorDetailFrom.get(ViolatorDetail_.documentSeries), violatorDetailFrom.get(ViolatorDetail_.documentNumber)),
                            value
                    )
            );
        };
    }

    public Specification<Violator> birthAfter(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.greaterThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Violator> birthBefore(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.lessThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Violator> withAdmCaseId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return root.get(Violator_.admCase).in(value);
        };
    }

    public Specification<Violator> withNationalityId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.nationalityId), value)
            );
        };
    }

    public Specification<Violator> withGenderId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.genderId), value)
            );
        };
    }

    public Specification<Violator> withPinpp(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.pinpp), value)
            );
        };
    }

    public Specification<Violator> withFirstNameLike(@Nullable String value) {
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

    public Specification<Violator> withSecondNameLike(@Nullable String value) {
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

    public Specification<Violator> withLastNameLike(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.startWith(value);

            return joinWithPerson(root, query, cb,
                    violatorPersonFrom -> cb.or(
                            cb.like(violatorPersonFrom.get(Person_.lastNameKir), likeValue),
                            cb.like(violatorPersonFrom.get(Person_.lastNameLat), likeValue)
                    )
            );
        };
    }

    private Predicate joinWithPerson(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Person>, Predicate> function) {
        Join<Violator, Person> personJoin = SpecificationsHelper.getExistJoin(root, Violator_.person);
        return function.apply(personJoin);
    }

    private Predicate joinWithAdmCase(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, AdmCase>, Predicate> function) {
        Join<Violator, AdmCase> admCaseJoin = SpecificationsHelper.getExistJoin(root, Violator_.admCase);
        return function.apply(admCaseJoin);
    }

    private Predicate joinWithAllDecision(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Decision>, Predicate> function) {
        Join<Violator, Decision> decisionJoin = SpecificationsHelper.getExistJoin(root, Violator_.decisions);
        return function.apply(decisionJoin);
    }

//    private Predicate joinWithResolution(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<AdmCase, Resolution>, Predicate> function) {
//        return joinWithAdmCase(root, query, cb, admCaseFrom -> {
//            Join<AdmCase, Resolution> admCaseJoin = SpecificationsHelper.getExistJoin(admCaseFrom, AdmCase_.resolutions);
//            return function.apply(admCaseJoin);
//        });
//    }

    private Predicate joinWithViolatorDetail(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, ViolatorDetail>, Predicate> function) {
        Join<Violator, ViolatorDetail> violatorDetailJoin = SpecificationsHelper.getExistJoin(root, Violator_.violatorDetails);
        return function.apply(violatorDetailJoin);
    }

    private Predicate joinWithProtocol(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<ViolatorDetail, Protocol>, Predicate> function) {
        return joinWithViolatorDetail(root, query, cb, violatorDetailJoin -> {
            Join<ViolatorDetail, Protocol> protocolJoin = SpecificationsHelper.getExistJoin(violatorDetailJoin, ViolatorDetail_.protocols);
            return function.apply(protocolJoin);
        });
    }

    private Predicate joinWithUbddData(Root<Violator> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, ProtocolUbddData>, Predicate> function) {
        return joinWithProtocol(root, query, cb, protocolJoin -> {
            Join<Protocol, ProtocolUbddData> ubddJoin = SpecificationsHelper.getExistJoin(protocolJoin, Protocol_.ubddData);
            return function.apply(ubddJoin);
        });
    }
}
