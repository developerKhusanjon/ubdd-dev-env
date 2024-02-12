package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementHistory;

public interface MibCardMovementHistoryRepository extends JpaRepository<MibCardMovementHistory, Long> {
}
