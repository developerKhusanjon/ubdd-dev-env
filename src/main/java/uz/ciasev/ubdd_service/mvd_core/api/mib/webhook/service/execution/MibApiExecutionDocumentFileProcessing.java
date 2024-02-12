package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution;

import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;

public interface MibApiExecutionDocumentFileProcessing {

    void process(MibCardMovement cardMovement, MibRequestDTO requestDTO);
}
