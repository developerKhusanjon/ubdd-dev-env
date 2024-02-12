package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Address_;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.Person_;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields_;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart_;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Invoice_;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement_;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard_;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification_;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail_;
import uz.ciasev.ubdd_service.entity.participant.Participant_;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.Protocol_;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment_;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment_;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail_;
import uz.ciasev.ubdd_service.entity.victim.Victim_;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail_;
import uz.ciasev.ubdd_service.entity.violator.Violator_;
import uz.ciasev.ubdd_service.exception.SearchFiltersNotSetException;
import uz.ciasev.ubdd_service.service.mib.MibValidationService;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Component
public class DecisionSpecifications {

    public Specification<Decision> withMibMovementSpec(Specification<MibCardMovement> decisionSpec) {
        return (root, query, cb) -> joinPenaltyMibCard(root, query, cb, join -> {
            Root<MibCardMovement> rootMovement = query.from(MibCardMovement.class);

            return cb.and(
                    cb.equal(join.get(MibExecutionCard_.id), rootMovement.get(MibCardMovement_.cardId)),
                    decisionSpec.toPredicate(rootMovement, query, cb)
            );
        });
    }

    public Specification<Decision> decisionMailList() {

        return SpecificationsCombiner.andAll(
                withStatusAlias(AdmStatusAlias.DECISION_MADE),
                activeOnly(),
                withPunishmentIsPenalty(),
                notCourt(),
                Specification.not(withDecisionHasMail(false))
        );
    }

    public Specification<Decision> decisionPossiblyToBeSendToMib() {
//        LocalDate executionFromDateAfter = LocalDate.now().minusDays(65);
        LocalDate executionFromDateAfter = MibValidationService.minExecutionDateForNotification();
        LocalDate executionFromDateBefore = MibValidationService.maxExecutionDateForNotification();

        return SpecificationsCombiner.andAll(
                suitForMibSend(),
                withStatusAlias(AdmStatusAlias.DECISION_MADE, AdmStatusAlias.IN_EXECUTION_PROCESS),
                withExecutionFromDateAfter(executionFromDateAfter),
                withExecutionFromDateBefore(executionFromDateBefore),
                Specification.not(withDecisionHasMail(true))
        );
    }

    public Specification<Decision> withVoluntaryPaymentPeriodHasExpired() {
        LocalDate executionFromDateBefore = LocalDate.now().minusDays(MibValidationService.getMibSendMinDays());
        return withExecutionFromDateBefore(executionFromDateBefore);
    }

    public Specification<Decision> decisionForAutoSendToMib(Map<Long, LocalDate> map) {
        List<Specification<Decision>> specs = new ArrayList<>();

        for (Map.Entry<Long, LocalDate> entry : map.entrySet()) {
            specs.add(findDecisionForMIB(entry.getKey(), entry.getValue()));
        }

        Specification<Decision> orSpec = null;
        for (Specification<Decision> spec : specs) {
            if (orSpec == null) {
                orSpec = spec;
            } else {
                orSpec = orSpec.or(spec);
            }
        }

        return SpecificationsCombiner.andAll(
                suitForMibSend(),
                withStatusAlias(AdmStatusAlias.DECISION_MADE, AdmStatusAlias.IN_EXECUTION_PROCESS),
                orSpec,
//                withResolutionOrganId(12L),
//                withExecutionFromDateAfter(executionFromDateAfter),
//                withExecutionFromDateBefore(executionFromDateBefore),
//                .and(withHandleByMibPreSend(true)) # если смс не было, но есть почта все равно можно отправлять
                Specification.not(withDecisionHasBeenSentToMib())
        );
    }

    public Specification<Decision> suitForMibSend() {
        return activeOnly()
                .and(isMaterialResolution().or(notCourt()))
                .and(withPunishmentIsPenalty())
                .and(Specification.not(withPunishmentStatus(AdmStatusAlias.EXECUTED)))
                ;
    }

    public Specification<Decision> notCourt() {
        return Specification.not(withResolutionOrganId(20L));
    }


    // Решение вынесенное судом в результатае рассмотрения жалобы нарушителя
    public Specification<Decision> isMaterialResolution() {
        return (root, query, cb) -> {
            Subquery<CourtMaterialFields> subquery = query.subquery(CourtMaterialFields.class);
            Root<CourtMaterialFields> subqueryRoot = subquery.from(CourtMaterialFields.class);
            subquery.select(subqueryRoot);
            Predicate predicate = cb.equal(subqueryRoot.get(CourtMaterialFields_.resolutionId), root.get(Decision_.resolution));
            subquery.where(predicate);
            return cb.exists(subquery);

        };
    }

    public Specification<Decision> withDecisionHasBeenSentToMib() {

        return (root, query, cb) -> {

            Subquery<MibCardMovement> subquery = query.subquery(MibCardMovement.class);
            Root<MibCardMovement> subqueryRoot = subquery.from(MibCardMovement.class);

            Join<MibCardMovement, MibExecutionCard> cards = subqueryRoot.join(MibCardMovement_.card);

            subquery.select(subqueryRoot);

            subquery.where(
                    cb.and(
                            cb.equal(cards.get(MibExecutionCard_.decisionId), root.get(Decision_.id)),
                            cb.not(subqueryRoot.get(MibCardMovement_.isNotTakeForAutosending))
                    )
            );

            return cb.exists(subquery);
        };
    }

    public Specification<Decision> withHandleByMibPreSend(Boolean value) {

        return (root, query, cb) -> SpecificationsHelper.getNullableBoolSpecification(root, cb, Decision_.handleByMibPreSend, value);
    }

    public Specification<Decision> withDecisionHasMail(boolean mibMails) {

        return (root, query, cb) -> {
            Subquery<MailNotification> subquery = query.subquery(MailNotification.class);
            Root<MailNotification> subqueryRoot = subquery.from(MailNotification.class);
            subquery.select(subqueryRoot);

            Predicate predicate = cb.equal(subqueryRoot.get(MailNotification_.decisionId), root.get(Decision_.id));
            if (mibMails) {
                predicate = cb.and(
                        predicate,
                        cb.equal(subqueryRoot.get(MailNotification_.notificationTypeAlias), NotificationTypeAlias.MIB_PRE_SEND)
                );
            }
            subquery.where(predicate);
            return cb.exists(subquery);
        };
    }

//    public Specification<Decision> withProtocolSeriesAndNumber(@NotNull String series, @NotNull String number) {
//        return (root, query, cb) -> {
//            return joinWithProtocol(
//                    root, query, cb,
//                    join -> cb.and(
//                        cb.equal(join.get(Protocol_.series), series),
//                        cb.equal(join.get(Protocol_.number), number)
//                )
//            );
//        };
//    }

    public Specification<Decision> withExecutionFromDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateGreatSpecification(root, cb, Decision_.executionFromDate, value);
    }

    public Specification<Decision> withExecutionFromDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateLessSpecification(root, cb, Decision_.executionFromDate, value);
    }

    public Specification<Decision> withExecutedDateDateAfter(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateGreatSpecification(root, cb, Decision_.executedDate, value);
    }

    public Specification<Decision> withExecutedDateDateBefore(LocalDate value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableDateLessSpecification(root, cb, Decision_.executedDate, value);
    }

    public Specification<Decision> withStatusAlias(AdmStatusAlias... values) {
        return (root, query, cb) -> {
            if (values.length == 0) {
                return cb.conjunction();
            }

            return root.get(Decision_.statusId).in(AdmStatusAlias.getIds(values));
//            return joinWithAdmStatus(root, query, cb,
//                    join -> cb.and(join.get(AdmStatus_.alias).in(Set.of(values)))
//            );
        };
    }

    private Specification<Decision> findDecisionForMIB(Long organId, LocalDate minDate) {

        return Specification
                .where(withOrganId(organId))
                .and(withExecutionFromDateBefore(minDate));
//        return (root, query, cb) -> {
//            Join<Decision, Resolution> resolutionJoin = root.join(Decision_.RESOLUTION);
//            Join<Resolution, Organ> organJoin = resolutionJoin.join(Resolution_.ORGAN);
//
//            return SpecificationsHelper.getNullableDateLessSpecification(root, cb, root.get(Decision_.EXECUTED_DATE),
//                    LocalDate.now().minusDays(organJoin.get(Organ_.SEND_CARD_TO_MI_B)));
//        };
    }

    public Specification<Decision> withStatusAlias(AdmStatusAlias value) {
//        if (value == null) {
//            return (root, query, cb) -> cb.conjunction();
//        }
//
//        return withStatusAlias(value);

        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Decision_.statusId), value.getId());
//            return joinWithAdmStatus(root, query, cb,
//                    join -> cb.equal(join.get(AdmStatus_.alias), value)
//            );
        };

    }

    public Specification<Decision> withPunishmentStatus(AdmStatusAlias value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
//            return joinWithPunishmentStatus(root, query, cb,
//                    join -> cb.equal(join.get(AdmStatus_.alias), value)
//            );
            return joinWithPunishment(root, query, cb,
                    join -> cb.equal(join.get(Punishment_.statusId), value.getId())
            );
        };
    }

    public Specification<Decision> withPunishmentIsMain() {
        return (root, query, cb) -> {
            return joinWithPunishment(root, query, cb,
                    join -> cb.isTrue(join.get(Punishment_.isMain))
            );
        };
    }

    public Specification<Decision> withPunishmentIsPenalty() {
        return (root, query, cb) -> {
            return joinWithPenalty(root, query, cb,
                    join -> cb.conjunction()
            );
        };
    }

    public Specification<Decision> inUserVisibility(User user) {

        if (user == null) {
            return (root, query, cb) -> {
                return cb.conjunction();
            };
        }

        return SpecificationsCombiner.andAll(
                withOrganId(user.getOrganId()),
                withDepartmentId(user.getDepartmentId()),
                withRegionId(user.getRegionId()),
                withDistrictId(user.getDistrictId())
        );
    }

    //

    public Specification<Decision> activeOnly() {
        return withIsActive(true)
                .and(withIsArchived(false));
    }

    public Specification<Decision> withIsActive(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithResolution(root, query, cb,
                    join -> cb.equal(join.get(Resolution_.isActive), value)
            );
        };
    }

    public Specification<Decision> withIsArchived(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolator(root, query, cb,
                    join -> cb.equal(join.get(Violator_.isArchived), value)
            );
        };
    }

    public Specification<Decision> withExecutedDateIsNull() {
        return (root, query, cb) -> cb.isNull(root.get(Decision_.executedDate));
    }

    public Specification<Decision> withViolatorNationalityId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.equal(join.get(Person_.nationalityId), value)
            );
        };
    }

    public Specification<Decision> withVictimFilterParams(Map<String, String> filterValues) {
        return (root, query, cb) -> {
            if (filterValues == null) {
                return cb.conjunction();
            }
            return joinWithProtocol(root, query, cb, violatorDetailProtocolFrom -> {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<VictimDetail> subRoot = subquery.from(VictimDetail.class);
                subRoot.join(VictimDetail_.protocol);
                Join<VictimDetail, Victim> victimJoin = subRoot.join(VictimDetail_.victim);
                Join<Victim, Person> personJoin = victimJoin.join(Victim_.person);
                subquery.select(subRoot.get(VictimDetail_.protocolId));

                List<Predicate> predicates = new ArrayList<>();
                AtomicInteger paramCount = new AtomicInteger();
                filterValues.forEach((keyField, value) -> {
                    switch (keyField.trim()) {
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
                if (paramCount.intValue() == filterValues.size()) {
                    throw new SearchFiltersNotSetException();
                }
                subquery.where(predicates.toArray(new Predicate[0]));
                return violatorDetailProtocolFrom.get(Protocol_.ID).in(subquery);
            });
        };
    }

    public Specification<Decision> withParticipantFilterParams(Map<String, String> filterValues) {
        return (root, query, cb) -> {
            if (filterValues == null) {
                return cb.conjunction();
            }
            return joinWithProtocol(root, query, cb, violatorDetailProtocolFrom -> {

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
                if (paramCount.intValue() == filterValues.size()) {
                    throw new SearchFiltersNotSetException();
                }
                subquery.where(predicates.toArray(new Predicate[0]));
                return violatorDetailProtocolFrom.get(Protocol_.ID).in(subquery);
            });
        };
    }

    public Specification<Decision> withViolatorGenderId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.equal(join.get(Person_.genderId), value)
            );
        };
    }

    public Specification<Decision> withViolatorFirstNameLike(String value) {
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

    public Specification<Decision> withViolatorSecondNameLike(String value) {
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

    public Specification<Decision> withViolatorLastNameLike(String value) {
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
        };
    }

    public Specification<Decision> withStatusIdIn(Set<Long> values) {
        return (root, query, cb) -> {
            if (values.size() == 0) {
                return cb.conjunction();
            }

            return root.get(Decision_.statusId).in(values);
        };
    }

    public Specification<Decision> withMainPunishmentTypeId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPunishment(root, query, cb,
                    join -> cb.and(
//                            cb.isTrue(join.get(Punishment_.isMain)),
                            cb.equal(join.get(Punishment_.punishmentTypeId), value)
                    )
            );
        };
    }

    public Specification<Decision> withNumber(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Decision_.number, value);
    }

    public Specification<Decision> withSeries(String value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableStringSpecification(root, cb, Decision_.series, value);
    }

    public Specification<Decision> withDecisionTypeId(Long value) {
        return (root, query, cb) -> SpecificationsHelper.getNullableLongSpecification(root, cb, Decision_.decisionTypeId, value);
    }

    public Specification<Decision> withArticlePartIdIn(Set<Long> values) {
        return (root, query, cb) -> {
            if (values.size() == 0) {
                return cb.conjunction();
            }
            return joinWithArticlePart(root, query, cb,
                    join -> cb.and(join.get(ArticlePart_.id).in(values))
            );
        };
    }

    public Specification<Decision> withInvoiceSerial(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithInvoice(root, query, cb,
                    join -> cb.equal(join.get(Invoice_.invoiceSerial), value)
            );
        };
    }

    public Specification<Decision> withPenaltyLastPayTimeAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPenalty(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(PenaltyPunishment_.lastPayTime)), value)
            );
        };
    }

    public Specification<Decision> withPenaltyLastPayTimeBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithPenalty(root, query, cb,
                    join -> cb.lessThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(PenaltyPunishment_.lastPayTime)), value)
            );
        };
    }

    public Specification<Decision> withAdmCaseId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.id), value)
            );
        };
    }

    public Specification<Decision> withUserId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.equal(join.get(Resolution_.userId), value)
            );
        };
    }

    public Specification<Decision> withResolutionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Decision_.resolutionId), value);
        };
    }

    public Specification<Decision> withViolatorId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(Decision_.violatorId), value);
        };
    }

    public Specification<Decision> withAdmCaseClaimId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.claimId), value)
            );
        };
    }

    public Specification<Decision> withResolutionTimeAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<Decision> withResolutionTimeBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.lessThanOrEqualTo(SpecificationsHelper.toDate(cb, join.get(Resolution_.resolutionTime)), value)
            );
        };
    }

    public Specification<Decision> withResolutionOrganId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithResolution(root, query, cb,
                    join -> cb.equal(join.get(Resolution_.organId), value)
            );
        };
    }

    public Specification<Decision> withViolatorBirthDateAfter(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.greaterThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Decision> withViolatorBirthDateBefore(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorPerson(root, query, cb,
                    join -> cb.lessThanOrEqualTo(join.get(Person_.birthDate), value)
            );
        };
    }

    public Specification<Decision> withViolatorDocumentNumber(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorDetail(root, query, cb,
                    join -> cb.equal(join.get(ViolatorDetail_.documentNumber), value)
            );
        };
    }

    public Specification<Decision> withViolatorDocumentSeries(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorDetail(root, query, cb,
                    join -> cb.equal(join.get(ViolatorDetail_.documentSeries), value)
            );
        };
    }


    // VISIBILITY BY ADM CASE

    public Specification<Decision> withOrganId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.organId), value)
            );
        };
    }

    public Specification<Decision> withDepartmentId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.departmentId), value)
            );
        };
    }

    public Specification<Decision> withRegionId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.regionId), value)
            );
        };
    }

    public Specification<Decision> withDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithAdmCase(root, query, cb,
                    join -> cb.equal(join.get(AdmCase_.districtId), value)
            );
        };
    }

    public Specification<Decision> withProtocolArticlePartIdIn(@Nullable Set<Long> values) {
        return (root, query, cb) -> {
            if (values == null || values.size() == 0) {
                return cb.conjunction();
            }
            return joinWithProtocol(root, query, cb,
                    join -> cb.and(join.get(Protocol_.articlePartId).in(values))
            );
        };
    }

    public Specification<Decision> withViolatorAddressDistrictId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorAddress(root, query, cb,
                    join -> cb.equal(join.get(Address_.districtId), value)
            );
        };
    }

    public Specification<Decision> withViolatorAddressRegionId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorAddress(root, query, cb,
                    join -> cb.equal(join.get(Address_.regionId), value)
            );
        };
    }

    public Specification<Decision> withViolatorAddressCountryId(@Nullable Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return joinWithViolatorAddress(root, query, cb,
                    join -> cb.equal(join.get(Address_.countryId), value)
            );
        };
    }


    // JOINS

    private Predicate joinWithProtocol(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<ViolatorDetail, Protocol>, Predicate> function) {
        return joinWithViolatorDetail(root, query, cb, violatorDetailJoin -> {
            Join<ViolatorDetail, Protocol> join = SpecificationsHelper.getExistJoin(violatorDetailJoin, ViolatorDetail_.protocols);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolatorDetail(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, ViolatorDetail>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorDetailJoin -> {
            Join<Violator, ViolatorDetail> join = SpecificationsHelper.getExistJoin(violatorDetailJoin, Violator_.violatorDetails);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolator(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Violator>, Predicate> function) {
        Join<Decision, Violator> join = SpecificationsHelper.getExistJoin(root, Decision_.violator);
        return function.apply(join);
    }

    private Predicate joinWithViolatorPerson(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Person>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Person> join = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.person);
            return function.apply(join);
        });
    }

    private Predicate joinWithViolatorAddress(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Violator, Address>, Predicate> function) {
        return joinWithViolator(root, query, cb, violatorJoin -> {
            Join<Violator, Address> join = SpecificationsHelper.getExistJoin(violatorJoin, Violator_.actualAddress);
            return function.apply(join);
        });
    }

    private Predicate joinWithAdmStatus(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, AdmStatus>, Predicate> function) {
        Join<Decision, AdmStatus> join = SpecificationsHelper.getExistJoin(root, Decision_.status);
        return function.apply(join);
    }

    private Predicate joinWithPunishment(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Punishment>, Predicate> function) {
        Join<Decision, Punishment> join = SpecificationsHelper.getExistJoin(root, Decision_.punishments);
        return function.apply(join);
    }

    private Predicate joinWithPenalty(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Punishment, PenaltyPunishment>, Predicate> function) {
        return joinWithPunishment(root, query, cb, joinPunishment -> {
            Join<Punishment, PenaltyPunishment> join = SpecificationsHelper.getExistJoin(joinPunishment, Punishment_.penalty);
            return function.apply(join);
        });
    }

    private Predicate joinWithPunishmentStatus(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Punishment, AdmStatus>, Predicate> function) {
        return joinWithPunishment(root, query, cb, joinPunishment -> {
            Join<Punishment, AdmStatus> join = SpecificationsHelper.getExistJoin(joinPunishment, Punishment_.status);
            return function.apply(join);
        });
    }

    private Predicate joinWithInvoice(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<PenaltyPunishment, Invoice>, Predicate> function) {
        return joinWithPenalty(root, query, cb, joinPenalty -> {
            Join<PenaltyPunishment, Invoice> join = SpecificationsHelper.getExistJoin(joinPenalty, PenaltyPunishment_.invoices);
            return function.apply(join);
        });
    }

    private Predicate joinWithArticlePart(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, ArticlePart>, Predicate> function) {
        Join<Decision, ArticlePart> join = SpecificationsHelper.getExistJoin(root, Decision_.articlePart);
        return function.apply(join);
    }

    private Predicate joinWithResolution(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, Resolution>, Predicate> function) {
        Join<Decision, Resolution> join = SpecificationsHelper.getExistJoin(root, Decision_.resolution);
        return function.apply(join);
    }

    private Predicate joinWithAdmCase(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Resolution, AdmCase>, Predicate> function) {
        return joinWithResolution(root, query, cb, joinAdm -> {
            Join<Resolution, AdmCase> join = SpecificationsHelper.getExistJoin(joinAdm, Resolution_.admCase);
            return function.apply(join);
        });
    }

    private Predicate joinPenaltyMibCard(Root<Decision> root, CriteriaQuery<?> query, CriteriaBuilder cb, Function<From<Decision, MibExecutionCard>, Predicate> function) {
        Join<Decision, MibExecutionCard> join = SpecificationsHelper.getExistJoin(root, Decision_.mibExecutionCards);
        return function.apply(join);
    }
}
