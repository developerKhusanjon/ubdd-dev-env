package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.protocol;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseCreatedResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmTerminationDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;

@Validated
public interface MibApiAdmService {

    void execution(Long decisionId, MibRequestDTO result);

    void decisionPdf(Long decisionId, byte[] fileContent);

    MibAdmCaseCreatedResponseDTO createAdmCase(MibAdmCaseRequestDTO request);

    void terminateResolution(MibAdmTerminationDTO dto);
}