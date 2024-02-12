package uz.ciasev.ubdd_service.service.violator;

import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorResponseDTO;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface ViolatorDTOService {

    ViolatorResponseDTO findDTOById(Long id);

    List<ViolatorResponseDTO> findAllDTOByAdmCaseId(Long admCaseId);

    ViolatorResponseDTO convertToDTO(Violator victim);

    ViolatorListResponseDTO convertToListDTO(Violator victim);

}