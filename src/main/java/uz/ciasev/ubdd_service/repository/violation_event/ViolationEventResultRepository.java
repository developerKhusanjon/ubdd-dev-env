package uz.ciasev.ubdd_service.repository.violation_event;

import org.springframework.data.jpa.repository.*;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;

public interface ViolationEventResultRepository extends JpaRepository<ViolationEventResult, Long>, JpaSpecificationExecutor<ViolationEventResult> {

    @Modifying
    @Query("UPDATE ViolationEventResult r " +
            "SET r.isActive = FALSE " +
            "WHERE r.violationEventId = :violationEventId ")
    void setActiveToFalseForAllByViolationEventId(Long violationEventId);
}
