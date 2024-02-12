package uz.ciasev.ubdd_service.repository.signature;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureEvent;

public interface DigitalSignatureEventRepository extends JpaRepository<DigitalSignatureEvent, Long> {
}
