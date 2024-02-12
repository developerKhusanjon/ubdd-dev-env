package uz.ciasev.ubdd_service.service.resolution.decision;
import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.*;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionFullListProjection;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedDecisionDTO;
import uz.ciasev.ubdd_service.service.search.decision.DecisionFullListResponseDTO;

import java.util.List;
import java.util.function.Supplier;

public interface DecisionDTOService {

    DecisionDetailResponseDTO buildDetailById(Supplier<Decision> supplier);

    List<DecisionListResponseDTO> buildList(Supplier<List<Decision>> supplier);

    DecisionListResponseDTO buildListForCreate(Supplier<CreatedDecisionDTO> supplier);

    Page<DecisionFullListResponseDTO> buildFullProjectionPage(Supplier<Page<DecisionFullListProjection>> supplier);

    List<DecisionFullListResponseDTO> buildFullProjectionList(Supplier<List<DecisionFullListProjection>> supplier);

    List<CourtMaterialListResponseDTO> buildMaterialList(Supplier<List<CourtMaterial>> supplier);

    DecisionListResponseDTO convertToListDTO(Decision decision);

    PunishmentDetailResponseDTO convertToDetailDTO(Punishment punishment);

    PunishmentListResponseDTO convertToListDTO(Punishment punishment);

    ForceExecutionResponseDTO convertToListDTO(ForceExecution forceExecution);
}
