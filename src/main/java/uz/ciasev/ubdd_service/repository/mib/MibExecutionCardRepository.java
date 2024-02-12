package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;

import java.util.List;
import java.util.Optional;

public interface MibExecutionCardRepository extends JpaRepository<MibExecutionCard, Long> {

    Optional<MibExecutionCard> findByDecisionId(Long id);

    Optional<MibExecutionCard> findByCompensationId(Long id);

    @Query("SELECT mc.decision.resolution.admCase.organ FROM MibExecutionCard mc WHERE mc = :card")
    Optional<Organ> findAdmCaseOrganByDecisionCard(MibExecutionCard card);

    @Query("SELECT mc.compensation.decision.resolution.admCase.organ FROM MibExecutionCard mc WHERE mc = :card")
    Optional<Organ> findAdmCaseOrganByCompensationCard(MibExecutionCard card);

    @Query("SELECT mc.decision.resolution.admCase FROM MibExecutionCard mc WHERE mc = :card")
    Optional<AdmCase> findAdmCaseByDecisionCard(MibExecutionCard card);

    @Query("SELECT mc.compensation.decision.resolution.admCase FROM MibExecutionCard mc WHERE mc = :card")
    Optional<AdmCase> findAdmCaseByCompensationCard(MibExecutionCard card);

    @Query("SELECT mced.evidenceDecision.resolution.admCase FROM EvidenceDecisionMib mced WHERE mced.card = :card")
    List<AdmCase> findAdmCaseByEvidenceDecisionCard(MibExecutionCard card, Pageable pageable);

    @Query("SELECT card " +
            "FROM MibExecutionCard card " +
            "LEFT JOIN card.compensation comp " +
            "WHERE card.decisionId = :decisionId " +
            "   OR comp.decisionId = :decisionId ")
    List<MibExecutionCard> findPenaltyAndCompensationByDecisionId(Long decisionId);
}
