package uz.ciasev.ubdd_service.service.victim;

import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimRequestCoreDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface VictimService {

    VictimResponseDTO findDTOById(Long id);

    List<VictimResponseDTO> findAllDTOByAdmCaseId(Long caseId);

    List<VictimResponseDTO> getFindVictims(Long admCaseId, Long violatorId, Long protocolId);

    Victim getOrSave(Victim victim);

    Victim findById(Long id);

    Long findByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    void delete(Victim victim);

    List<Victim> findByAdmCaseId(Long admCaseId);

    List<String> findVictimsPinppByProtocolId(Long protocolId);

    List<Victim> findByViolatorId(Violator violator);

    List<Victim> findByAdmCaseOrViolatorOfProtocolViolator(Long admCaseId, Long violatorId, Long protocolId);

    Victim mergeTo(Victim oldVictim, AdmCase newAdmCase);

    void updateVictimPostAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    void updateVictimActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    void updateVictim(User user, Long id, VictimRequestCoreDTO requestDTO);

    VictimResponseDTO convertToDTO(Victim victim);

    VictimListResponseDTO convertToListDTO(Victim victim);

    VictimListResponseDTO findListDTOById(Long id);
}
