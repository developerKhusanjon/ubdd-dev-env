package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.CompensationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.AdmEntityList;

import java.util.List;

public interface ResolutionValidationService {

    void validateConsider(User user, AdmCase admCase, SingleResolutionRequestDTO requestDTO);

    //only for court
    @Deprecated
    void validateDecision(DecisionRequestDTO requestDTO);

    void validateDecisionByProtocol(Violator violator, DecisionRequestDTO decision);

    void validateCourtCompensationsByViolators(AdmEntityList<Violator> violators, List<? extends CompensationRequestDTO> compensations);

    void validateOrganCompensationsByViolator(Violator violator, List<? extends CompensationRequestDTO> compensations);

    void validateEvidenceDecisions(AdmEntityList<Evidence> evidences, List<? extends EvidenceDecisionRequestDTO> evidenceDecisions);
}
