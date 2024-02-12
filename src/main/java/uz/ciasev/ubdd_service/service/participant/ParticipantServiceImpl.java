package uz.ciasev.ubdd_service.service.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.participant.ParticipantDetailRepository;
import uz.ciasev.ubdd_service.repository.participant.ParticipantRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantDetailRepository participantDetailRepository;
    private final AddressService addressService;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;

    @Override
    public List<ParticipantResponseDTO> findAllDTOByAdmCaseId(Long admCaseId) {
        return participantRepository.findByAdmCaseId(admCaseId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ParticipantListResponseDTO> getFindParticipants(Long admCaseId, Long violatorId, Long protocolId) {
        return null;
    }

    @Override
    public ParticipantResponseDTO findDTOById(Long id) {
        return convertToDTO(findById(id));
    }

    @Override
    public void updateParticipantActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        Participant participant = findById(id);
        AdmCase admCase = admCaseService.getById(participant.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_PARTICIPANT_DETAIL, admCase);

        addressService.update(participant.getActualAddressId(), requestDTO);
    }

    @Override
    public void updateParticipant(User user, Long id, ParticipantRequestDTO requestDTO) {
        Participant participant = findById(id);
        AdmCase admCase = admCaseService.getById(participant.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_PARTICIPANT_DETAIL, admCase);

        participantRepository.save(requestDTO.applyTo(participant));
    }

    @Override
    public Participant findById(Long id) {
        return participantRepository.findById(id).orElseThrow(() -> new EntityByIdNotFound(Participant.class, id));
    }

    @Override
    public Participant getOrSave(Participant participant) {

        Optional<Participant> participantOptional = participantRepository
                .findByAdmCaseIdAndPersonIdAndParticipantTypeId(participant.getAdmCaseId(), participant.getPersonId(), participant.getParticipantTypeId());

        if (participantOptional.isPresent()) {
            return participantOptional.get();
        }

        participant.setId(null);
        participant.setMergedToParticipantId(null);

        participant.setActualAddress(addressService.copyOfId(participant.getActualAddressId()));

        return participantRepository.saveAndFlush(participant);
    }

    @Override
    public List<Participant> findAllByAdmCaseId(Long admCaseId) {
        return participantRepository.findByAdmCaseId(admCaseId);
    }

    @Override
    public void delete(Participant participant) {
        participantRepository.delete(participant);
    }

    @Override
    @Transactional
    public Participant mergeTo(Participant participant, AdmCase toAdmCase) {
        Participant newParticipant = getOrSave(participant.toBuilder().admCase(toAdmCase).build());
        participantDetailRepository.mergeAllTo(participant.getId(), newParticipant.getId());
        participantRepository.mergedTo(participant.getId(), newParticipant.getId());
        return newParticipant;
    }

    @Override
    public ParticipantResponseDTO convertToDTO(Participant participant) {
        if (participant == null) {
            return null;
        }

        return new ParticipantResponseDTO(participant, participant.getPerson(), addressService.findDTOById(participant.getActualAddressId()));
    }

    @Override
    public ParticipantListResponseDTO convertToListDTO(Participant victim) {
        if (victim == null) {
            return null;
        }

        return new ParticipantListResponseDTO(victim, victim.getPerson());
    }
}