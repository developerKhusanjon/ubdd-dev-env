package uz.ciasev.ubdd_service.service.victim;

import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;

import java.util.List;

public interface VictimDetailService {

    VictimDetailResponseDTO findDTOById(Long id);

    List<VictimDetailResponseDTO> findAllDTOByProtocolId(Long protocolId);

    VictimDetail updateVictimDetail(User user, Long victimId, Long protocolId, VictimDetailRequestDTO requestDTO);

    VictimDetail findById(Long id);

    VictimDetail save(VictimDetail victimDetail);

    void delete(VictimDetail victimDetail);

    List<VictimDetail> findAllByProtocolId(Long protocolId);

    VictimDetail findByVictimIdAndProtocolId(Long victimId, Long protocolId);

    boolean existsByVictim(Victim victim);

    VictimDetailResponseDTO convertToDTO(VictimDetail victimDetail);

    boolean existsByProtocolIdAndPersonId(Long protocolId, Long personId);
}
