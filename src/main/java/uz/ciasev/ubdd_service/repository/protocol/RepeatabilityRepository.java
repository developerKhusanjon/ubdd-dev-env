package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.Repeatability;
import uz.ciasev.ubdd_service.entity.protocol.RepeatabilityPdfProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

public interface RepeatabilityRepository extends JpaRepository<Repeatability, Long> {

    @Query("SELECT r.fromProtocolId as fromProtocolId, " +
            "r.decisionId as decisionId, " +
            "r.protocolId as protocolId, " +
            "r.articlePart as articlePart, " +
            "r.violationTime as violationTime " +
            "FROM Repeatability r " +
            "WHERE r.protocol = :protocol")
    List<RepeatabilityPdfProjection> findRepeatabilityArticlePartsByProtocol(Protocol protocol);

    @Query("SELECT r.fromProtocolId as fromProtocolId, " +
            "r.decisionId as decisionId, " +
            "r.protocolId as protocolId, " +
            "r.articlePart as articlePart, " +
            "r.violationTime as violationTime " +
            "FROM Repeatability r " +
            "WHERE r.protocol = :protocol OR r.decision = :decision " +
            "ORDER BY r.decisionId ")
    List<RepeatabilityPdfProjection> findRepeatabilityArticlePartsByDecisionId(Decision decision, Protocol protocol);

    List<Repeatability> findAllByProtocolId(Long protocolId);

    List<Repeatability> findAllByDecisionId(Long protocolId);

    boolean existsByProtocolId(Long protocolId);

    @Modifying
    @Query("DELETE FROM Repeatability r WHERE r.protocol = :protocol")
    void deleteByProtocol(Protocol protocol);

    @Query("SELECT r.fromProtocolId FROM Repeatability r WHERE r.protocolId = :protocolId")
    List<Long> getRepeatabilityProtocolsIdByProtocol(Long protocolId);
}
