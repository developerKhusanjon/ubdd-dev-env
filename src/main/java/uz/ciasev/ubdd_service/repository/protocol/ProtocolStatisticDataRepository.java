package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;

import java.util.Optional;

public interface ProtocolStatisticDataRepository extends JpaRepository<ProtocolStatisticData, Long> {

    Optional<ProtocolStatisticData> findByProtocolId(Long id);
}
