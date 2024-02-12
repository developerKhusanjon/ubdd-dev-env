package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.mib.MibAutoSendLog;

public interface MibAutoSendLogRepository extends JpaRepository<MibAutoSendLog, Long> {
}
