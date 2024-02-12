package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface UbddDataToProtocolBindService {

    void save(User user, UbddDataToProtocolBindDTO dto);
    void save(User user, Long protocolId, UbddDataBind dto);
    Optional<UbddDataToProtocolBind> findByProtocolId(Long protocolId);
    Optional<UbddDataToProtocolBindDTO> findDTOByProtocolId(Long protocolId);
    Optional<UbddDataToProtocolBindInternalDTO> findInternalDTOByProtocolId(Long protocolId);
}
