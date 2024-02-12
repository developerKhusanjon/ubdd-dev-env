package uz.ciasev.ubdd_service.repository.autocon;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSendingStatusLog;

public interface AutoconSendingStatusLogRepository extends JpaRepository<AutoconSendingStatusLog, Long> {
}
