package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;

import java.util.List;

public interface MibCardMovementReturnRequestRepository extends JpaRepository<MibCardMovementReturnRequest, Long> {
    List<MibCardMovementReturnRequest> findAllByMovementId(Long movementId, Sort by);

    @Query("SELECT returnRequest " +
            "FROM MibCardMovementReturnRequest returnRequest " +
            "WHERE returnRequest.movement.cardId = :cardId")
    List<MibCardMovementReturnRequest> findAllByCardId(Long cardId, Sort by);
}
