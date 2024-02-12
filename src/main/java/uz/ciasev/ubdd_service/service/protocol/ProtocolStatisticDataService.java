package uz.ciasev.ubdd_service.service.protocol;

import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolStatisticDataResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;

import java.util.List;
import java.util.Optional;

public interface ProtocolStatisticDataService extends ProtocolDataService<ProtocolStatisticData> {

    List<ProtocolStatisticData> findAll();
    ProtocolStatisticData save(ProtocolStatisticData protocolStatisticData);
    void delete(Long id);

    Optional<ProtocolStatisticData> findByProtocolId(Long id);
    ProtocolStatisticDataResponseDTO getResponseDTO(ProtocolStatisticData data);
}
