package uz.ciasev.ubdd_service.entity.history;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.SlaveAdmEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "h_entity_removal_registration")
@Data
@SuperBuilder()
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class EntityRemovalRegistration extends Registration {

    @Getter
    @Enumerated(EnumType.STRING)
    private EntityNameAlias masterEntityType;

    @Getter
    private Long masterEntityId;

    @Getter
    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityType;

    @Getter
    private Long entityId;

    private String info;

    public EntityRemovalRegistration(SlaveAdmEntity entity, String info) {
        this.entityId = entity.getId();
        this.entityType = entity.getEntityNameAlias();
        this.masterEntityId = entity.getMaster().getId();
        this.masterEntityType = entity.getMaster().getEntityNameAlias();
        this.info = info;
    }

}
