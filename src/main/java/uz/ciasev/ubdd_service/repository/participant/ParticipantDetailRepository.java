package uz.ciasev.ubdd_service.repository.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;

import java.util.List;
import java.util.Optional;

public interface ParticipantDetailRepository extends JpaRepository<ParticipantDetail, Long> {

    List<ParticipantDetail> findAllByProtocolId(@Param("protocolId") Long protocolId);

    Optional<ParticipantDetail> findByParticipantIdAndProtocolId(@Param("participantId") Long participantId,
                                                                 @Param("protocolId") Long protocolId);

    boolean existsByParticipantId(Long participantId);

    @Modifying
    @Query(value = "UPDATE ParticipantDetail SET participantId = :toParticipantId WHERE participantId = :fromParticipantId")
    void mergeAllTo(@Param("fromParticipantId") Long fromParticipantId, @Param("toParticipantId") Long toParticipantId);

    @Query("SELECT pd FROM ParticipantDetail pd " +
            "WHERE pd.protocolId = :protocolId " +
            "AND pd.participant.personId = :personId " +
            "AND pd.participant.participantTypeId = :participantTypeId")
    Optional<ParticipantDetail> findByProtocolIdAndPersonIdAndParticipantTypeId(@Param("protocolId") Long protocolId,
                                                                                @Param("personId") Long personId,
                                                                                @Param("participantTypeId") Long participantTypeId);
}
