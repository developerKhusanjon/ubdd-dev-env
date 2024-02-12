package uz.ciasev.ubdd_service.service.main.protocol;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;

public interface ProtocolCreateAdditionalValidationService {

    void validateExternalId(ProtocolRequestDTO dto);
}
