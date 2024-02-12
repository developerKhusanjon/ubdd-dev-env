package uz.ciasev.ubdd_service.repository.violation_event;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;

public interface ViolationEventAnnulmentRepository extends JpaRepository<ViolationEventAnnulment, Long> {
}
