package uz.ciasev.ubdd_service.service.main.protocol;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

@Validated
public interface ProtocolCreateService {

    Protocol createElectronProtocol(@Inspector(message = ErrorCode.CREATED_USER_HAS_NO_ORGAN) User user, ProtocolRequestDTO protocolDTO);

    Protocol editElectronProtocol(@Inspector(message = ErrorCode.CREATED_USER_HAS_NO_ORGAN) User user, ProtocolRequestDTO protocolDTO);

}
