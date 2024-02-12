package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;

import java.util.Optional;

public interface ProtocolUbddDataRepository extends JpaRepository<ProtocolUbddData, Long> {

    Optional<ProtocolUbddData> findByProtocolId(Long id);
}
