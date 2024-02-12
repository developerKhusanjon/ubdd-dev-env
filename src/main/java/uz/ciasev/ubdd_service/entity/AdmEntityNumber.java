package uz.ciasev.ubdd_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "adm_entity_number")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmEntityNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_entity_number_id_seq")
    @SequenceGenerator(name = "adm_entity_number_id_seq", sequenceName = "adm_entity_number_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EntityNameAlias alias;

    private Long number;

    private Long year;
}
