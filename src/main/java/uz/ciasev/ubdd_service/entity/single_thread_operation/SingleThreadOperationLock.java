package uz.ciasev.ubdd_service.entity.single_thread_operation;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "single_thread_operation_lock")
@NoArgsConstructor
@AllArgsConstructor
public class SingleThreadOperationLock implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "single_thread_operation_lock_id_seq")
    @SequenceGenerator(name = "single_thread_operation_lock_id_seq", sequenceName = "single_thread_operation_lock_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Getter
    private Long resourceId;

    @Getter
    @Column(name = "operation_type_id")
    private Long operationTypeId;

    public SingleThreadOperationLock(Long resourceId, SingleThreadOperationTypeAlias alias) {
        this.createdTime = LocalDateTime.now();
        this.resourceId = resourceId;
        this.operationTypeId = alias.getId();
    }

}
