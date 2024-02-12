package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

@Getter
public class MibAdmCaseCreatedResponseDTO {

    private final String protocolSeries;

    private final String protocolNumber;

    private final Long decisionId;

    public MibAdmCaseCreatedResponseDTO(Protocol protocol, Decision decision) {
        this.protocolSeries = protocol.getSeries();
        this.protocolNumber = protocol.getNumber();
        this.decisionId = decision.getId();
    }
}
