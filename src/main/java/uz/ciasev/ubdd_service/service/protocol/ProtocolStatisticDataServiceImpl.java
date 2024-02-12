package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolStatisticDataResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolStatisticDataRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolStatisticDataServiceImpl implements ProtocolStatisticDataService {

    private final ProtocolStatisticDataRepository repository;

    @Override
    public ProtocolStatisticDataResponseDTO getResponseDTO(ProtocolStatisticData data) {
        return (data != null) ? new ProtocolStatisticDataResponseDTO(data) : null;
    }

    @Override
    public Optional<ProtocolStatisticData> findByProtocolId(Long id) {
        return repository.findByProtocolId(id);
    }

    @Override
    public List<ProtocolStatisticData> findAll() {
        return repository.findAll();
    }

    @Override
    public ProtocolStatisticData save(ProtocolStatisticData protocolStatisticData) {
        return repository.save(protocolStatisticData);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
