package uz.ciasev.ubdd_service.service.search.mib;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyDecisionForMibProjection;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.temporary.UbddDecison202311Projection;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.dict.OrganRepository;
import uz.ciasev.ubdd_service.repository.mib.MibCardMovementRepository;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.notification.DecisionNotificationService;
import uz.ciasev.ubdd_service.specifications.DecisionSpecifications;
import uz.ciasev.ubdd_service.specifications.MibMovementSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MibSearchServiceImpl implements MibSearchService {

    private final DecisionRepository decisionRepository;
    private final DecisionSpecifications decisionSpecifications;
    private final MibMovementSpecifications mibMovementSpecifications;
    private final MibCardMovementRepository mibCardMovementRepository;
    private final DecisionNotificationService notificationService;
    private final OrganRepository organRepository;

    private final OptionalFilterHelper<MibCardMovement> mibCardMovementFilterHelper;
    private final FilterHelper<Decision> decisionFilterHelper;

    public MibSearchServiceImpl(DecisionRepository decisionRepository, DecisionSpecifications decisionSpecifications, MibMovementSpecifications mibMovementSpecifications, MibCardMovementRepository mibCardMovementRepository, DecisionNotificationService notificationService, OrganRepository organRepository) {
        this.decisionRepository = decisionRepository;
        this.decisionSpecifications = decisionSpecifications;
        this.mibMovementSpecifications = mibMovementSpecifications;
        this.mibCardMovementRepository = mibCardMovementRepository;
        this.notificationService = notificationService;
        this.organRepository = organRepository;


        mibCardMovementFilterHelper = new OptionalFilterHelper<MibCardMovement>(
                Pair.of("mibCaseNumber", new StringFilter<>(mibMovementSpecifications::withMibCaseNumber)),
                Pair.of("mibCaseStatusId", new LongFilter<>(mibMovementSpecifications::withMibCaseStatusId)),
                Pair.of("mibCaseStatusTypeId", new LongFilter<>(mibMovementSpecifications::withMibCaseStatusTypeId)),
                Pair.of("sendTimeFrom", new DateFilter<>(mibMovementSpecifications::withSendTimeAfter)),
                Pair.of("sendTimeTo", new DateFilter<>(mibMovementSpecifications::withSendTimeAfter)),
                Pair.of("returnTimeFrom", new DateFilter<>(mibMovementSpecifications::withReturnTimeAfter)),
                Pair.of("returnTimeTo", new DateFilter<>(mibMovementSpecifications::withReturnTimeAfter)),
                Pair.of("acceptTimeFrom", new DateFilter<>(mibMovementSpecifications::withAcceptTimeAfter)),
                Pair.of("acceptTimeTo", new DateFilter<>(mibMovementSpecifications::withAcceptTimeAfter))
        );

        decisionFilterHelper = new FilterHelper<Decision>(
                Pair.of("decisionNumber", new StringFilter<>(decisionSpecifications::withNumber)),
                Pair.of("resolutionTimeFrom", new DateFilter<>(decisionSpecifications::withResolutionTimeAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<>(decisionSpecifications::withResolutionTimeBefore)),
                Pair.of("organId", new LongFilter<>(decisionSpecifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<>(decisionSpecifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<>(decisionSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(decisionSpecifications::withDistrictId))
        );
    }


    @Override
    public List<Long> findAllDecisionForMibPreSend() {
        return decisionRepository.findAllId(
                decisionSpecifications.decisionPossiblyToBeSendToMib()
                        .and(decisionSpecifications.withHandleByMibPreSend(false))
        );
    }

    @Override
    public Map<Long, String> findAllDecisionByProtocolNumbers() {
        Map<Long, String> map = new HashMap<>();
        for (UbddDecison202311Projection projection : decisionRepository.findAllByProtocolNumbers()) {
            map.put(projection.getId(), projection.getNumber());
        }
        return map;
    }

    @Override
    public Page<Long> getDecisionsForAutoSendToMib(Pageable pageable) {
        var organMap = organRepository.findAll().stream()
                .collect(Collectors.toMap(Organ::getId, organ -> LocalDate.now().minusDays(organ.getSendCardToMIB())));

        return decisionRepository.findAllId(decisionSpecifications.decisionForAutoSendToMib(organMap), pageable);
    }


    //    @Override
    public List<MibCardMovement> findMibMovementList(User user, Map<String, String> filters, Pageable pageable) {
//    public List<MibPenaltyListResponseDTO> findUserPenaltyForMibList(User user, Map<String, String> filters, Pageable pageable) {
        Specification<Decision> decisionSpec = decisionSpecifications.activeOnly()
                .and(decisionSpecifications.inUserVisibility(user))
                .and(decisionSpecifications.notCourt().or(decisionSpecifications.isMaterialResolution()))
                .and(decisionSpecifications.withPunishmentIsPenalty());

        Specification<MibCardMovement> mibMovementSpec = mibMovementSpecifications.withDecisionSpec(decisionSpec);
//                .and(mibMovementSpecifications.withIsActive(true))
        Specification<MibCardMovement> filtersSpec = mibCardMovementFilterHelper.getParamsSpecification(filters)
                .and(mibCardMovementFilterHelper.getParamsSpecification(filters));

        return mibCardMovementRepository.findAll(mibMovementSpec);
    }


    @Override
    public List<Long> findUserPenaltyDecisionForMibIds(User user, Map<String, String> filters, Pageable pageable) {
        Specification<Decision> decisionSpec = decisionSpecifications.inUserVisibility(user)
                .and(decisionSpecifications.withStatusAlias(AdmStatusAlias.DECISION_MADE, AdmStatusAlias.IN_EXECUTION_PROCESS, AdmStatusAlias.RETURN_FROM_MIB))
                .and(decisionSpecifications.suitForMibSend())
                .and(decisionSpecifications.withVoluntaryPaymentPeriodHasExpired())
                .and(decisionFilterHelper.getParamsSpecification(filters));


        Optional<Specification<MibCardMovement>> mibMovementSpecOpt = mibCardMovementFilterHelper.getParamsSpecificationOptional(filters);
        if (mibMovementSpecOpt.isPresent()) {
            Specification<MibCardMovement> mibMovementSpec = mibMovementSpecOpt.get().and(mibMovementSpecifications.withIsActive(true));
            decisionSpec = decisionSpec.and(decisionSpecifications.withMibMovementSpec(mibMovementSpec));
        }

        return decisionRepository.findAllIdList(decisionSpec, pageable);
    }


    @Override
    public List<PenaltyForSendToMibResponseDTO> findUserPenaltyDecisionForMibList(User user, Map<String, String> filters, Pageable pageable) {
        List<PenaltyDecisionForMibProjection> decisionsData = decisionRepository.findDecisionForMibList(findUserPenaltyDecisionForMibIds(user, filters, pageable), pageable.getSort());
        Map<MibNotificationTypeAlias, Map<Long, DecisionNotification>> presetNotificationsData = getPresetDecisionNotifications(decisionsData);

        return decisionsData.stream()
                .map(d -> new PenaltyForSendToMibResponseDTO(d, getNotificationFromMap(presetNotificationsData, d)))
                .collect(Collectors.toList());

    }

    private DecisionNotification getNotificationFromMap(Map<MibNotificationTypeAlias, Map<Long, DecisionNotification>> presetNotificationsData, PenaltyDecisionForMibProjection decisionData) {
        return Optional.ofNullable(presetNotificationsData.get(decisionData.getPresetNotificationTypeAlias()))
                .map(n -> n.get(decisionData.getPresetNotificationId()))
                .orElse(null);
    }

    private Map<MibNotificationTypeAlias, Map<Long, DecisionNotification>> getPresetDecisionNotifications(List<PenaltyDecisionForMibProjection> decisionsData) {
        Map<MibNotificationTypeAlias, List<PenaltyDecisionForMibProjection>> presetNotificationsId = decisionsData.stream()
                .filter(n -> n.getPresetNotificationId() != null)
                .collect(Collectors.groupingBy(PenaltyDecisionForMibProjection::getPresetNotificationTypeAlias));

        Map<MibNotificationTypeAlias, Map<Long, DecisionNotification>> presetNotificationsData = presetNotificationsId.entrySet().stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        notificationService.getAllByChannelAndIds(e.getKey(), e.getValue().stream().map(PenaltyDecisionForMibProjection::getPresetNotificationId).collect(Collectors.toList()))
                )).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().collect(Collectors.toMap(DecisionNotification::getId, n -> n))
                ));

        return presetNotificationsData;
    }


}
