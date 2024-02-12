package uz.ciasev.ubdd_service.entity.status;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.utils.deserializer.dict.AdmStatusCacheDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "entity_status_accumulator")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@JsonDeserialize(using = AdmStatusCacheDeserializer.class)
public class EntityStatusAccumulator {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityType;

    @Getter
    private Long entityId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "adm_status_id")
    private AdmStatus admStatus;
}
