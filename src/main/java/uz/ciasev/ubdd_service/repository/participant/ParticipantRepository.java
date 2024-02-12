package uz.ciasev.ubdd_service.repository.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.participant.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByAdmCaseIdAndPersonIdAndParticipantTypeId(Long admCaseId, Long personId, Long participantTypeId);

    List<Participant> findByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    List<Participant> findByPersonId(Long personId);

    List<Participant> findByAdmCaseId(Long admCaseId);

    @Modifying
    @Query(value = "UPDATE Participant SET mergedToParticipantId = :toParticipantId WHERE id = :fromParticipantId")
    void mergedTo(@Param("fromParticipantId") Long fromParticipantId, @Param("toParticipantId") Long toParticipantId);
}
