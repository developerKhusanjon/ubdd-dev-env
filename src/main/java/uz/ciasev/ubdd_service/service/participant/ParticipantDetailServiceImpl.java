package uz.ciasev.ubdd_service.service.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ParticipantDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.participant.ParticipantDetailRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantDetailServiceImpl implements ParticipantDetailService {

    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseService admCaseService;
    private final AddressService addressService;
    private final ParticipantDetailRepository participantDetailRepository;

    @Override
    public List<ParticipantDetail> findAllByProtocolId(Long protocolId) {
        return participantDetailRepository.findAllByProtocolId(protocolId);
    }

    @Override
    public ParticipantDetail findById(Long id) {
        return participantDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ParticipantDetail.class, id));
    }

    @Override
    public ParticipantDetailResponseDTO findDTOById(Long id) {
        return convertToDTO(findById(id));
    }

    @Override
    public List<ParticipantDetailResponseDTO> findAllDTOByProtocolId(Long protocolId) {
        return findAllByProtocolId(protocolId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantDetail findByParticipantIdAndProtocolId(Long participantId, Long protocolId) {
        return participantDetailRepository
                .findByParticipantIdAndProtocolId(participantId, protocolId)
                .orElseThrow(() -> new EntityByParamsNotFound(ParticipantDetail.class, "participantId", participantId, "protocolId", protocolId));
    }

    @Override
    public ParticipantDetail updateParticipantDetail(User user, Long participantId, Long protocolId, ParticipantDetailRequestDTO requestDTO) {
        ParticipantDetail participantDetail = findByParticipantIdAndProtocolId(participantId, protocolId);

        AdmCase admCase = admCaseService.getById(participantDetail.getParticipant().getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_PARTICIPANT_DETAIL, admCase);

        return participantDetailRepository.save(requestDTO.applyTo(participantDetail));
    }

    @Override
    public void delete(ParticipantDetail participantDetail) {
        participantDetailRepository.delete(participantDetail);
    }

    @Override
    public boolean existsByParticipant(Participant participant) {
        return participantDetailRepository.existsByParticipantId(participant.getId());
    }

    @Override
    public ParticipantDetail save(ParticipantDetail participantDetail) {
        return participantDetailRepository.save(participantDetail);
    }

    @Override
    public ParticipantDetailResponseDTO convertToDTO(ParticipantDetail participantDetail) {
        if (participantDetail == null) {
            return null;
        }

        Participant participant = participantDetail.getParticipant();
        return new ParticipantDetailResponseDTO(
                participantDetail,
                participant,
                participant.getPerson(),
                Optional.ofNullable(participantDetail.getDocumentGivenAddressId()).map(addressService::findDTOById).orElse(null)
        );
    }

    @Override
    public boolean existsByProtocolIdAndPersonId(Long protocolId, Long personId, Long participantTypeId) {
        Optional<ParticipantDetail> rsl = participantDetailRepository.
                findByProtocolIdAndPersonIdAndParticipantTypeId(
                        protocolId,
                        personId,
                        participantTypeId);
        return rsl.isPresent();
    }
}
