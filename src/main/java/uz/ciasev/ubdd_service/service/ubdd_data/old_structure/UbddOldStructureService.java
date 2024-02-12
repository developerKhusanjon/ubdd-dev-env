package uz.ciasev.ubdd_service.service.ubdd_data.old_structure;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestAdditionalDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface UbddOldStructureService {

//    void saveProtocolBind(User user, Long protocolId, ProtocolRequestAdditionalDTO additional);

    void createBindForNewProtocol(User user, Protocol protocol, ProtocolRequestAdditionalDTO additional);

    void updateProtocolAdditional(User user, Long protocolId, ProtocolRequestAdditionalDTO requestDTO);

    Optional<ProtocolUbddDataView> findByProtocolId(Long id);
}
