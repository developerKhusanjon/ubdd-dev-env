package uz.ciasev.ubdd_service.entity.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "h_manual_execution_delete_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class ManualExecutionDeleteRegistration extends Registration {

    private Long entityId;

    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityType;
//    private Long punishmentId;
//    private Long compensationId;

    @Enumerated(value = EnumType.STRING)
    private ManualExecutionDeleteRegistrationType type;
}
