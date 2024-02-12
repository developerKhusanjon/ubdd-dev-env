package uz.ciasev.ubdd_service.service.main.protocol;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestAdditionalDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;

@Validated
public interface ProtocolAdditionalService {

    void createProtocolAdditional(User user, Protocol protocol, ProtocolRequestAdditionalDTO additional);

    Protocol updateProtocolAdditional(User user, Long protocolId, ProtocolRequestAdditionalDTO requestDTO);
}
