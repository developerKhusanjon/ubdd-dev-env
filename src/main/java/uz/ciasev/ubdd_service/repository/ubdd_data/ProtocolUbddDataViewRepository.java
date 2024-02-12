package uz.ciasev.ubdd_service.repository.ubdd_data;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;

import java.util.Optional;

public interface ProtocolUbddDataViewRepository extends JpaRepository<ProtocolUbddDataView, Long> {
    Optional<ProtocolUbddDataView> findByProtocolId(Long id);
}
