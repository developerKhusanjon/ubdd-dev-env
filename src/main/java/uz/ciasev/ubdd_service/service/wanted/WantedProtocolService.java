package uz.ciasev.ubdd_service.service.wanted;

import uz.ciasev.ubdd_service.mvd_core.api.wanted.WantedDTO;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import java.util.List;

public interface WantedProtocolService {

    void registerWanted(Protocol protocol, List<WantedDTO> dtoList);

    WantedProtocol findById(Long id);

    List<WantedProtocol> findAllByProtocolId(Long id);

    List<WantedDTO> findAllDTOByProtocolId(Long id);

    void closeWanted(Long id);
}
