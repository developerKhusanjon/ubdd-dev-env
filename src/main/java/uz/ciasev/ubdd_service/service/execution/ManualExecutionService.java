package uz.ciasev.ubdd_service.service.execution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.execution.ArrestExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.execution.DeportationExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.execution.WithdrawalExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentExecuteWithoutBillingDTO;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.search.arrest.ArrestFullListResponseDTO;

import java.util.Map;

public interface ManualExecutionService {

    void executionWithdrawal(User user, Long id, WithdrawalExecutionRequestDTO requestDTO);

    void executionArrest(User user, Long id, ArrestExecutionRequestDTO requestDTO);

    void executionDeportation(User user, Long id, DeportationExecutionRequestDTO requestDTO);

    Page<ArrestFullListResponseDTO> globalSearchByFilter(Map<String, String> filterValues, Pageable pageable);

    void deleteManualBilling(User user, Long punishmentId);

    Punishment executeWithoutBilling(User user, Long punishmentId, PunishmentExecuteWithoutBillingDTO dto);

    void cancelGaiFileForceExecution(Long punishmentId);
}
