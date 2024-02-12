package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.dto.internal.request.mib.CourtMibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

import java.util.List;


public interface MibCourtService {

    boolean isMibRequestIdProcessed(Long mibRequestId);

    MibCardMovement openCourtMibCard(List<EvidenceDecision> evidenceDecisions, CourtMibCardRequestDTO requestDTO);

    void openCourtMibCard(Decision decision, CourtMibCardRequestDTO requestDTO);

    void openCourtMibCard(Compensation compensation, CourtMibCardRequestDTO requestDTO);
}
