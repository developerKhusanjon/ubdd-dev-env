package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.MibCardDecisionDTO;

@Data
@AllArgsConstructor
public class MibSendDecisionRequestApiDTO {

    private DocumentEnvelopeDTO documentEnvelope;

    public MibSendDecisionRequestApiDTO(MibCardDecisionDTO d, SignatureDTO signature) {
        this.documentEnvelope = new DocumentEnvelopeDTO(d, signature);
    }
}
