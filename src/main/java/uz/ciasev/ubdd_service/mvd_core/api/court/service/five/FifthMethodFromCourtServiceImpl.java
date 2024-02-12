package uz.ciasev.ubdd_service.mvd_core.api.court.service.five;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtMibDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtPerformancesRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.mib.CourtMibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.entity.dict.court.CourtPerformanceType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtPerformanceTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.service.dict.court.CourtPerformanceTypeService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransGeographyService;
import uz.ciasev.ubdd_service.service.mib.MibCourtService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FifthMethodFromCourtServiceImpl implements FifthMethodFromCourtService {

//    private static final Long MIB_DAMAGE = 9L;
//    private static final Long MIB_PENALTY = 6L;
//    private final List<Long> evidenceTypes = List.of(7L, 8L, 10L);
//    private final List<Long> permittedTypes = List.of(1L, 3L, 4L, 5L);

    private final MibCourtService mibService;
    private final DecisionService decisionService;
    private final CompensationService compensationService;
    private final CourtTransGeographyService geographyService;
    private final EvidenceDecisionService evidenceDecisionService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
    private final ResolutionService resolutionService;
    private final CourtPerformanceTypeService performanceTypeService;


    @Transactional
    public void accept(FiveCourtMibDTO requestDTO) {
        courtDuplicateRequestService.checkAndRemember(requestDTO);

        var caseId = requestDTO.getCaseId();
        var claimId = requestDTO.getClaimId();


        Resolution resolution = resolutionService.getByCaseAndClaimIds(caseId, claimId);

        requestDTO.getPerformanceList().forEach(performance -> {
            handelPerformance(claimId, resolution, performance);
        });

    }

    private void handelPerformance(Long claimId, Resolution resolution, FiveCourtPerformancesRequestDTO performance) {
        if (performance.getMibEnvelopeId() == null) {
            return;
        }

        CourtPerformanceType type = Optional.ofNullable(performance.getType()).map(performanceTypeService::getById)
                .orElseThrow(() -> new CourtValidationException("type field can not be null"));

        validate(performance, type);

        if (mibService.isMibRequestIdProcessed(performance.getMibEnvelopeId())) {
            return;
        }

        CourtMibCardRequestDTO mibCardRequestDTO = buildMibCard(performance);

        if (type.is(CourtPerformanceTypeAlias.PENALTY)) {

            Decision decision = decisionService.getByResolutionAndViolatorIds(resolution.getId(), performance.getViolatorId());
            mibService.openCourtMibCard(decision, mibCardRequestDTO);

        } else if (type.is(CourtPerformanceTypeAlias.COMPENSATION)) {

            Compensation compensation = compensationService.getGovByViolatorId(resolution.getId(), performance.getViolatorId());
            mibService.openCourtMibCard(compensation, mibCardRequestDTO);

        } else if (type.is(CourtPerformanceTypeAlias.EVIDENCE)) {

            List<EvidenceDecision> evidenceDecisions = findEvidenceDecisions(claimId, performance.getEvidenceIds());
            mibService.openCourtMibCard(evidenceDecisions, mibCardRequestDTO);

        } else {
            throw new CourtValidationException(MIB_TYPE_CANT_BE_PROCESSED);
        }
    }

    private CourtMibCardRequestDTO buildMibCard(FiveCourtPerformancesRequestDTO performance) {
        CourtTransDistrict courtDistrict = geographyService.getCourtDistrictByExternalId(performance.getExternalDistrictId());

        var mibCardRequestDTO = new CourtMibCardRequestDTO();

        mibCardRequestDTO.setRegion(courtDistrict.getRegion());
        mibCardRequestDTO.setDistrict(courtDistrict.getDistrict());
        mibCardRequestDTO.setMibRequestId(performance.getMibEnvelopeId());
        mibCardRequestDTO.setSendTime(performance.getSendTime());

        return mibCardRequestDTO;
    }

    private List<EvidenceDecision> findEvidenceDecisions(Long resolutionClaimId, List<Long> evidenceCourtIds) {
        return evidenceCourtIds
                .stream()
                .map(id -> evidenceDecisionService.findByEvidenceCourtId(resolutionClaimId, id))
                .collect(Collectors.toList());
    }

//    private void validate(FiveCourtMibDTO mibDTO) {
//        List<FiveCourtPerformancesRequestDTO> performanceList = mibDTO.getPerformanceList();
//        Optional<FiveCourtPerformancesRequestDTO> permittedType = performanceList
//                .stream()
//                .filter(p -> permittedTypes.contains(p.getType()))
//                .findFirst();
//
//        if (permittedType.isPresent())
//            throw new CourtValidationException(MIB_TYPE_CANT_BE_PROCESSED);
//
//        for (FiveCourtPerformancesRequestDTO performance : performanceList) {
//            if (evidenceTypes.contains(performance.getType()) && performance.getEvidenceIds().isEmpty())
//                throw new CourtValidationException(MIB_TYPE_NOT_PERMITTED);
//        }
//    }

    private void validate(FiveCourtPerformancesRequestDTO performance, CourtPerformanceType type) {
        if (performance.getMibEnvelopeId() == null) {
            throw new CourtValidationException("mibEnvelopeId field can not be null");
        }

        if (performance.getSendTime() == null) {
            throw new CourtValidationException("sendTime field can not be null");
        }

        if (performance.getExternalDistrictId() == null) {
            throw new CourtValidationException("regionId field can not be null");
        }

        if (type.is(CourtPerformanceTypeAlias.PERMITTED)) {
            throw new CourtValidationException("permitted type can not be processed");
        }

        if (type.oneOf(CourtPerformanceTypeAlias.PENALTY, CourtPerformanceTypeAlias.COMPENSATION)) {
            if (performance.getViolatorId() == null) {
                throw new CourtValidationException(String.format("violatorId field can not be null for type %s", performance.getType()));
            }
        }

        if (type.is(CourtPerformanceTypeAlias.EVIDENCE)) {
            if (performance.getEvidenceIds() == null || performance.getEvidenceIds().isEmpty()) {
                throw new CourtValidationException(String.format("evidenceIds field can not be null for type %s", performance.getType()));
            }
        }
    }
}
