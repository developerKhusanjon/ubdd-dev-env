package uz.ciasev.ubdd_service.repository.notification.manual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;

import java.util.List;

public interface ManualNotificationRepository extends JpaRepository<ManualNotification, Long>, JpaSpecificationExecutor<ManualNotification>, ManualNotificationCustomRepository {
    List<ManualNotification> findAllByDecisionId(Long decisionId);
}
