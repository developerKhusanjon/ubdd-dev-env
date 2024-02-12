package uz.ciasev.ubdd_service.service.participant;

import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface ParticipantDetailService {

    ParticipantDetailResponseDTO findDTOById(Long id);

    List<ParticipantDetailResponseDTO> findAllDTOByProtocolId(Long protocolId);

    ParticipantDetail findByParticipantIdAndProtocolId(Long participantId, Long protocolId);

    ParticipantDetail updateParticipantDetail(User user, Long participantId,  Long protocol, ParticipantDetailRequestDTO requestDTO);

    ParticipantDetail save(ParticipantDetail participantDetail);

    void delete(ParticipantDetail participantDetail);

    ParticipantDetail findById(Long id);

    List<ParticipantDetail> findAllByProtocolId(Long protocolId);

    boolean existsByParticipant(Participant participant);

    ParticipantDetailResponseDTO convertToDTO(ParticipantDetail participantDetail);

    boolean existsByProtocolIdAndPersonId(Long protocolId, Long personId, Long participantTypeId);
}
