package uz.ciasev.ubdd_service.specifications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Address_;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory_;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail_;
import uz.ciasev.ubdd_service.entity.participant.Participant_;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.protocol.Protocol_;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution_;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind_;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.User_;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail_;
import uz.ciasev.ubdd_service.entity.victim.Victim_;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;
import uz.ciasev.ubdd_service.exception.SearchFiltersNotSetException;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.service.dict.resolution.TerminationReasonDictionaryService;
import uz.ciasev.ubdd_service.service.execution.BillingEntityService;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProtocolSpecifications extends VisibilitySpecifications<Protocol> {

    private final InvoiceRepository invoiceRepository;
    private final BillingEntityService billingEntityService;
    private final TerminationReasonDictionaryService terminationReasonRepository;

    public Specification<Protocol> activeOnly() {
        return withIsArchived(false)
                .and((root, query, cb) -> cb.equal(root.get(Protocol_.isDeleted), false));
    }

    public Specification<Protocol> withIsJuridic(@Nullable Boolean value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableBoolSpecification(root, cb, Protocol_.isJuridic, value);
    }

    public Specification<Protocol> withIsMain(@Nullable Boolean value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableBoolSpecification(root, cb, Protocol_.isMain, value);
    }

    public Specification<Protocol> withIsArchived(@Nullable Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolator(root, query, cb,
                    violatorFrom -> cb.equal(violatorFrom.get(Violator_.isArchived), value)
            );
        };
    }

    public Specification<Protocol> withIsDeleted(@Nullable Boolean value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableBoolSpecification(root, cb, Protocol_.isDeleted, value);
    }

    public Specification<Protocol> withNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Protocol_.number, value);
    }

    public Specification<Protocol> withSeries(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Protocol_.series, value);
    }

    public Specification<Protocol> withOldNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Protocol_.oldNumber, value);
    }

    public Specification<Protocol> withOldSeries(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Protocol_.oldSeries, value);
    }

    public Specification<Protocol> createdAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, Protocol_.createdTime, value);
    }

    public Specification<Protocol> createdBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, Protocol_.createdTime, value);
    }

    public Specification<Protocol> violationTimeAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, Protocol_.violationTime, value);
    }

    public Specification<Protocol> violationTimeBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, Protocol_.violationTime, value);
    }

    public Specification<Protocol> registeredAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, Protocol_.registrationTime, value);
    }

    public Specification<Protocol> registeredAfter(LocalDateTime value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeGreatSpecification(root, cb, Protocol_.registrationTime, value);
    }

    public Specification<Protocol> registeredBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateTimeLessSpecification(root, cb, Protocol_.registrationTime, value);
    }

    public Specification<Protocol> withId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Protocol_.id, value);
    }

    public Specification<Protocol> withArticleId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Protocol_.articleId, value);
    }

    public Specification<Protocol> withArticleViolationTypeId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Protocol_.articleViolationTypeId, value);
    }

    public Specification<Protocol> withArticleViolationTypeIdExactly(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getExactlyLongSpecification(root, cb, Protocol_.articleViolationTypeId, value);
    }

    public Specification<Protocol> withArticlePartId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Protocol_.articlePartId, value);
    }

    public Specification<Protocol> withUserId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Protocol_.userId, value);
    }

    public Specification<Protocol> withUserWorkCertificate(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithUser(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(User_.workCertificate), value)
            );
        };
    }

    public Specification<Protocol> withViolatorDocumentTypeId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.personDocumentTypeId), value)
            );
        };
    }

    public Specification<Protocol> withUbddVehicleNumberLike(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            String likeValue = SpecificationsHelper.likeValue(value);

            return cb.like(root.get(Protocol_.vehicleNumber), likeValue);

//            return joinWithUbddData(root, query, cb,
//                    ubddDataFrom -> cb.like(ubddDataFrom.get(Protocol_.vehicleNumber), likeValue)
//            );
        };
    }

    public Specification<Protocol> withUbddVehicleNumberExactly(@Nullable String value) {
//        return (root, query, cb) -> leftJoinWithUbddData(
//                root, query, cb,
//                ubddDataFrom -> {
//                    if (value == null) {
//                        return cb.isNull(ubddDataFrom.get(Protocol_.vehicleNumber));
//                    }
//
//                    return cb.equal(ubddDataFrom.get(Protocol_.vehicleNumber), value);
//                });

        return (root, query, cb) -> {
            if (value == null) {
                return cb.isNull(root.get(Protocol_.vehicleNumber));
            }

            return cb.equal(root.get(Protocol_.vehicleNumber), value);
        };
    }

    public Specification<Protocol> withViolatorIdExactly(@Nullable Long value) {
        return (root, query, cb) -> {
            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.violatorId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorIdInExactly(@Nullable Collection<Long> value) {
        return (root, query, cb) -> {
            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> violatorDetailFrom.get(ViolatorDetail_.violatorId).in(value)
            );
        };
    }

    public Specification<Protocol> withViolatorDocumentSeries(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentSeries), value)
            );
        };
    }

    public Specification<Protocol> withVictimDocumentSeries(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            Join<VictimDetail, Protocol> join = query.from(VictimDetail.class)
                    .join(VictimDetail_.protocol)
                    .join(VictimDetail_.VICTIM);
            return cb.equal(join.get(VictimDetail_.DOCUMENT_SERIES), value);
        };
    }

    public Specification<Protocol> withViolatorDocumentNumber(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorDetail(root, query, cb,
                    violatorDetailFrom -> cb.equal(violatorDetailFrom.get(ViolatorDetail_.documentNumber), value)
            );
        };
    }

    public Specification<Protocol> withVictimFilterParams(Map<String, String> filterValues) {
        return (root, query, cb) -> {
            if (filterValues == null) {
                return cb.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<VictimDetail> subRoot = subquery.from(VictimDetail.class);
            subRoot.join(VictimDetail_.protocol);
            Join<VictimDetail, Victim> victimJoin = subRoot.join(VictimDetail_.victim);
            Join<Victim, Person> personJoin = victimJoin.join(Victim_.person);
            subquery.select(subRoot.get(VictimDetail_.protocolId));

            List<Predicate> predicates = new ArrayList<>();
            AtomicInteger paramCount = new AtomicInteger();
            filterValues.forEach((keyField, value) -> {
                switch (keyField) {
                    case "pinpp":
                        predicates.add(cb.equal(personJoin.get(Person_.pinpp), value));
                        break;
                    case "firstName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.firstNameKir), value),
                                cb.like(personJoin.get(Person_.firstNameLat), value)));
                        break;
                    case "secondName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.secondNameKir), value),
                                cb.like(personJoin.get(Person_.secondNameLat), value)));
                        break;
                    case "lastName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.lastNameKir), value),
                                cb.like(personJoin.get(Person_.lastNameLat), value)));
                        break;
                    case "birthDate":
                        predicates.add(cb.equal(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "birthDateFrom":
                        predicates.add(cb.greaterThanOrEqualTo(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "birthDateTo":
                        predicates.add(cb.greaterThanOrEqualTo(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "documentSeries":
                        predicates.add(cb.equal(subRoot.get(VictimDetail_.documentSeries), value));
                        break;
                    case "documentNumber":
                        predicates.add(cb.equal(subRoot.get(VictimDetail_.documentNumber), value));
                        break;
                    default:
                        paramCount.getAndIncrement();
                }
            });
            if (paramCount.intValue() != filterValues.size()) {
                throw new SearchFiltersNotSetException();
            }
            subquery.where(predicates.toArray(new Predicate[0]));
            return root.get(Protocol_.ID).in(subquery);
        };
    }

    public Specification<Protocol> withParticipantFilterParams(Map<String, String> filterValues) {
        return (root, query, cb) -> {
            if (filterValues == null) {
                return cb.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ParticipantDetail> subRoot = subquery.from(ParticipantDetail.class);
            subRoot.join(ParticipantDetail_.protocol);
            Join<ParticipantDetail, Participant> victimJoin = subRoot.join(ParticipantDetail_.participant);
            Join<Participant, Person> personJoin = victimJoin.join(Participant_.person);
            subquery.select(subRoot.get(ParticipantDetail_.protocolId));

            List<Predicate> predicates = new ArrayList<>();
            AtomicInteger paramCount = new AtomicInteger();
            filterValues.forEach((keyField, value) -> {
                switch (keyField) {
                    case "pinpp":
                        predicates.add(cb.equal(personJoin.get(Person_.pinpp), value));
                        break;
                    case "firstName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.firstNameKir), value),
                                cb.like(personJoin.get(Person_.firstNameLat), value)));
                        break;
                    case "secondName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.secondNameKir), value),
                                cb.like(personJoin.get(Person_.secondNameLat), value)));
                        break;
                    case "lastName":
                        predicates.add(cb.or(
                                cb.like(personJoin.get(Person_.lastNameKir), value),
                                cb.like(personJoin.get(Person_.lastNameLat), value)));
                        break;
                    case "birthDate":
                        predicates.add(cb.equal(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "birthDateFrom":
                        predicates.add(cb.greaterThanOrEqualTo(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "birthDateTo":
                        predicates.add(cb.greaterThanOrEqualTo(personJoin.get(Person_.birthDate), LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)));
                        break;
                    case "documentSeries":
                        predicates.add(cb.equal(subRoot.get(ParticipantDetail_.documentSeries), value));
                        break;
                    case "documentNumber":
                        predicates.add(cb.equal(subRoot.get(ParticipantDetail_.documentNumber), value));
                        break;
                    default:
                        paramCount.getAndIncrement();
                }
            });
            if (paramCount.intValue() != filterValues.size()) {
                throw new SearchFiltersNotSetException();
            }
            subquery.where(predicates.toArray(new Predicate[0]));
            return root.get(Protocol_.ID).in(subquery);
        };
    }

    public Specification<Protocol> withViolatorBirth(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Protocol> withViolatorBirthAfter(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.greaterThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Protocol> withViolatorBirthBefore(@Nullable LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.lessThanOrEqualTo(violatorPersonFrom.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Protocol> withAdmCaseId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolator(root, query, cb,
                    violatorFrom -> cb.equal(violatorFrom.get(Violator_.admCaseId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorAddressDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorAddress(root, query, cb,
                    join -> cb.equal(join.get(Address_.districtId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorAddressRegionId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorAddress(root, query, cb,
                    join -> cb.equal(join.get(Address_.regionId), value)
            );
        };
    }

    public Specification<Protocol> withAdmCaseStatusId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithAdmCase(root, query, cb,
                    admCaseFrom -> cb.equal(admCaseFrom.get(AdmCase_.statusId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorNationalityId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.nationalityId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorGenderId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.genderId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorPersonId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolator(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Violator_.personId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorBirthCountryId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorBirthAdders(root, query, cb,
                    violatorBirthAddersFrom -> cb.equal(violatorBirthAddersFrom.get(Address_.countryId), value)
            );
        };
    }

    public Specification<Protocol> withViolatorActualCountryId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorAddress(root, query, cb,
                    violatorActualAddress -> cb.equal(violatorActualAddress.get(Address_.countryId), value)
            );
        };
    }


    public Specification<Protocol> withViolatorPinpp(@Nullable String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    violatorPersonFrom -> cb.equal(violatorPersonFrom.get(Person_.pinpp), value)
            );
        };
    }

    public Specification<Protocol> withVictimPinpp(@Nullable String value) {
        return (root, query, cb) -> {
            Join<Object, Person> join = query.from(VictimDetail.class)
                    .join(VictimDetail_.protocol)
                    .join(VictimDetail_.VICTIM)
                    .join(Victim_.PERSON);
            return cb.equal(join.get(Person_.pinpp), value);
        };
    }

    public Specification<Protocol> withViolatorFirstNameLike(@Nullable String value) {
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


    public Specification<Protocol> withViolatorSecondNameLike(@Nullable String value) {
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

    public Specification<Protocol> withViolatorLastNameLike(@Nullable String value) {
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

    public Specification<Protocol> hasActiveResolution(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithResolution(root, query, cb,
                    from -> cb.equal(
                            from.get(Resolution_.isActive),
                            value
                    )
            );
        };
    }

    public Specification<Protocol> resolvedAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithResolution(root, query, cb,
                    from -> cb.greaterThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, from.get(Resolution_.resolutionTime)),
                            value
                    )
            );
        };
    }
    public Specification<Protocol> resolvedBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithResolution(root, query, cb,
                    from -> cb.lessThanOrEqualTo(
                            SpecificationsHelper.toDate(cb, from.get(Resolution_.resolutionTime)),
                            value
                    )
            );
        };
    }

    public Specification<Protocol> withDecisionInvoiceSerial(String value) {
        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            Optional<Invoice> invoiceOpt = invoiceRepository.findByInvoiceSerial(value);
            if (invoiceOpt.isEmpty()) {
                return cb.not(cb.conjunction());
            }

            Violator violator = billingEntityService.getOwnerViolator(invoiceOpt.get());

            return joinWithViolatorDetail(root, query, cb,
                    from -> cb.equal(
                            from.get(ViolatorDetail_.violatorId),
                            violator.getId()
                    )
            );
        };
    }

    public Specification<Protocol> withDecisionNumber(String value) {
        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            return joinWithDecision(root, query, cb,
                    from -> cb.equal(
                            from.get(Decision_.number),
                            value
                    )
            );
        };
    }

    public Specification<Protocol> withJuvenileViolator() {
        return (root, query, cb) -> {
            return joinWithAgeCategory(root, query, cb,
                    from -> cb.equal(
                            from.get(AgeCategory_.isJuvenile),
                            true
                    )
            );
        };
    }


    public Specification<Protocol> exceptTerminationByParticipateOfRepeatability(Boolean value) {
        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            return joinWithDecision(root, query, cb,
                    from -> cb.or(
                            from.get(Decision_.terminationReasonId).in(terminationReasonRepository.getParticipateOfRepeatabilityCached()),
                            cb.isNull(from.get(Decision_.terminationReasonId))
                    )
            );
        };
    }

    public Specification<Protocol> withUbddVehicleArrestId(Long value) {
        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            return joinWithUbddDataBind(root, query, cb,
                    from -> cb.equal(
                            from.get(UbddDataToProtocolBind_.vehicleArrestId),
                            value
                    )
            );
        };
    }

    private Predicate joinWithUser(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, User>, Predicate> function) {
        Join<Protocol, User> ubddDataJoin = SpecificationsHelper.getExistJoin(root, Protocol_.user);
        return function.apply(ubddDataJoin);
    }

    private Predicate leftJoinWithUbddData(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, ProtocolUbddData>, Predicate> function) {
        return joinWithUbddData(root, query, cb, JoinType.LEFT, function);
    }

    private Predicate joinWithUbddData(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, ProtocolUbddData>, Predicate> function) {
        return joinWithUbddData(root, query, cb, JoinType.INNER, function);
    }

    private Predicate joinWithUbddDataBind(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, UbddDataToProtocolBind>, Predicate> function) {
        Join<Protocol, UbddDataToProtocolBind> ubddDataJoin = SpecificationsHelper.getExistJoin(root, Protocol_.ubddDataBind);
        return function.apply(ubddDataJoin);
    }

    private Predicate joinWithUbddData(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, JoinType joinType, Function<From<Protocol, ProtocolUbddData>, Predicate> function) {
        Join<Protocol, ProtocolUbddData> ubddDataJoin = SpecificationsHelper.getExistJoin(root, Protocol_.ubddData, joinType);
        return function.apply(ubddDataJoin);
    }

    private Predicate joinWithViolatorDetail(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Protocol, ViolatorDetail>, Predicate> function) {
        Join<Protocol, ViolatorDetail> violatorDetailJoin = SpecificationsHelper.getExistJoin(root, Protocol_.violatorDetail);
        return function.apply(violatorDetailJoin);
    }

    private Predicate joinWithViolator(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<ViolatorDetail, Violator>, Predicate> function) {
        return joinWithViolatorDetail(root, query, cb, violatorDetailJoin -> {
            Join<ViolatorDetail, Violator> violatorJoin = SpecificationsHelper.getExistJoin(violatorDetailJoin, ViolatorDetail_.violator);
            return function.apply(violatorJoin);
        });
    }

    private Predicate joinWithViolatorAddress(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Address>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Address> join = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.actualAddress);
            return function.apply(join);
        });
    }

    private Predicate joinWithAgeCategory(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<ViolatorDetail, AgeCategory>, Predicate> function) {
        return joinWithViolatorDetail(root, query, cb, violatorDetailJoin -> {
            Join<ViolatorDetail, AgeCategory> violatorJoin = SpecificationsHelper.getExistJoin(violatorDetailJoin, ViolatorDetail_.ageCategory);
            return function.apply(violatorJoin);
        });
    }

    private Predicate joinWithAdmCase(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, AdmCase>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, AdmCase> admCaseJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.admCase);
            return function.apply(admCaseJoin);
        });
    }

    private Predicate joinWithDecision(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Decision>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Decision> admCaseJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.decisions);
            return function.apply(admCaseJoin);
        });
    }

    private Predicate joinWithResolution(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Resolution>, Predicate> function) {
        return joinWithDecision(root, query, cb, violatorJoin -> {
            Join<Decision, Resolution> admCaseJoin = SpecificationsHelper.getExistJoin(violatorJoin, Decision_.resolution);
            return function.apply(admCaseJoin);
        });
    }

    private Predicate joinWithResolutionUser(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Resolution, User>, Predicate> function) {
        return joinWithResolution(root, query, cb, violatorJoin -> {
            Join<Resolution, User> admCaseJoin = SpecificationsHelper.getExistJoin(violatorJoin, Resolution_.user);
            return function.apply(admCaseJoin);
        });
    }

    private Predicate joinWithViolatorPerson(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Person>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Person> personJoin = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.person);
            return function.apply(personJoin);
        });
    }

    private Predicate joinWithViolatorBirthAdders(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Person, Address>, Predicate> function) {
        return joinWithViolatorPerson(root, query, cb, violatorPersonJoin -> {
            Join<Person, Address> personBirthAddressJoin = SpecificationsHelper.getExistJoin(violatorPersonJoin, Person_.birthAddress);
            return function.apply(personBirthAddressJoin);
        });
    }
}
