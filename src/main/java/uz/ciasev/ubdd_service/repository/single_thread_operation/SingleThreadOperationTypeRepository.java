package uz.ciasev.ubdd_service.repository.single_thread_operation;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationType;

public interface SingleThreadOperationTypeRepository extends JpaRepository<SingleThreadOperationType, Long> {
}
