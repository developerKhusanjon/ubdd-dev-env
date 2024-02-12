package uz.ciasev.ubdd_service.service.participant;

import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface ParticipantService {

    List<ParticipantResponseDTO> findAllDTOByAdmCaseId(Long admCaseId);

    List<ParticipantListResponseDTO> getFindParticipants(Long admCaseId, Long violatorId, Long protocolId);

    ParticipantResponseDTO findDTOById(Long id);

    void updateParticipantActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    void updateParticipant(User user, Long id, ParticipantRequestDTO requestDTO);

    Participant findById(Long id);

    Participant getOrSave(Participant participant);

    List<Participant> findAllByAdmCaseId(Long admCaseId);

    void delete(Participant participant);

    Participant mergeTo(Participant participant, AdmCase toAdmCase);

    ParticipantResponseDTO convertToDTO(Participant victim);

    ParticipantListResponseDTO convertToListDTO(Participant victim);
}
