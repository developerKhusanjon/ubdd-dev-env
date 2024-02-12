package uz.ciasev.ubdd_service.service.resolution.compensation;

import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.CompensationDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.CompensationListResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;

import java.util.List;

public interface CompensationDTOService {

    CompensationDetailResponseDTO findDetailById(Long id);

//    List<CompensationListResponseDTO> findAllDTOByFilters(Long resolutionId, Long decisionId, Long violatorId, Long victimId);

    CompensationListResponseDTO convertToDTO(Compensation compensation);

    List<CompensationListResponseDTO> findAllDTOByDecisionId(Long decisionId);
}
