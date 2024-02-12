package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution;

import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibPayedAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MvdExecutionResponseDTO;

public interface MibApiExecutionService {

    MvdExecutionResponseDTO executionResultWebhook(Long requestId, MibRequestDTO result);

    MvdExecutionResponseDTO getPayedAmount(Long requestId, MibPayedAmountRequestDTO result);
}