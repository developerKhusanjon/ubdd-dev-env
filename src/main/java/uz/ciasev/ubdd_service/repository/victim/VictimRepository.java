package uz.ciasev.ubdd_service.repository.victim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.victim.Victim;

import java.util.List;
import java.util.Optional;

public interface VictimRepository extends JpaRepository<Victim, Long> {

    @Query("SELECT v.id " +
            " FROM Victim v " +
            "WHERE v.admCaseId = :admCaseId " +
            "  AND v.personId = :personId")
    Optional<Long> findIdByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    List<Victim> findByPersonId(Long personId);

    Optional<Victim> findByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    List<Victim> findByAdmCaseId(Long admCaseId);

    @Query("SELECT DISTINCT vd.victim.person.pinpp " +
            " FROM VictimDetail vd " +
            "WHERE vd.protocolId = :protocolId")
    List<String> findVictimsPinppByProtocolId(Long protocolId);

    @Query("SELECT DISTINCT v " +
            " FROM Victim v " +
            " JOIN VictimDetail vcd ON v.id = vcd.victimId " +
            " JOIN Protocol p ON vcd.protocolId = p.id " +
            " JOIN ViolatorDetail vld ON p.violatorDetailId = vld.id " +
            "WHERE (v.admCaseId = :admCaseId OR :admCaseId IS NULL) " +
            "  AND (vld.violatorId = :violatorId OR :violatorId IS NULL) " +
            "  AND (vcd.protocolId = :protocolId OR :protocolId IS NULL)" +
            "  AND p.isDeleted = FALSE")
    List<Victim> findByAdmCaseOrViolatorOrProtocol(Long admCaseId, Long violatorId, Long protocolId);

    @Modifying
    @Query(value = "UPDATE Victim " +
            "          SET mergedToVictimId = :toVictimId " +
            "        WHERE id = :fromVictimId")
    void mergedTo(@Param("fromVictimId") Long fromVictimId, @Param("toVictimId") Long toVictimId);
}