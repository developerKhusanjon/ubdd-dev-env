package uz.ciasev.ubdd_service.entity.single_thread_operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "single_thread_operation_type")
@NoArgsConstructor
@AllArgsConstructor
public class SingleThreadOperationType implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @Getter
    private Long id;

    private String name;
}
