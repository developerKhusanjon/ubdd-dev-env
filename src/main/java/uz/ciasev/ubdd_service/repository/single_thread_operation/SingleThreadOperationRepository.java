package uz.ciasev.ubdd_service.repository.single_thread_operation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationLock;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface SingleThreadOperationRepository extends JpaRepository<SingleThreadOperationLock, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SingleThreadOperationLock sto WHERE sto.createdTime < :upperLimit")
    void deleteFrozenOperations(@Param(value = "upperLimit") LocalDateTime upperLimit);
}
