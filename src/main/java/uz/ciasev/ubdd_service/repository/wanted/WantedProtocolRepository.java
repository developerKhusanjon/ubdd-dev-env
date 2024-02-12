package uz.ciasev.ubdd_service.repository.wanted;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;

import java.util.List;
import java.util.Optional;

public interface WantedProtocolRepository extends JpaRepository<WantedProtocol, Long> {

    Optional<WantedProtocol> findByProtocolIdAndExtId(Long protocolId, Long extId);
    boolean existsByProtocolIdAndExtId(Long protocolId, Long extId);

    @Query(value = "SELECT * FROM core_v0.wanted_protocols wlp " +
            "WHERE NOT EXISTS( " +
            "   SELECT 1 FROM core_v0.wanted_protocols_closed cls " +
            "   WHERE cls.wanted_protocol_id = wlp.id " +
            ")"
            , nativeQuery = true)
    List<WantedProtocol> findAllOpen();

    List<WantedProtocol> findAllByProtocolId(Long protocolId);
}
