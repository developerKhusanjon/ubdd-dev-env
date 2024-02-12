package uz.ciasev.ubdd_service.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alias_description")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AliasDescription implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alias_description_id_seq")
    @SequenceGenerator(name = "alias_description_id_seq", sequenceName = "alias_description_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    private String aliasName;

    @Getter
    private String aliasValue;

    @Getter
    private String label;

    @Getter
    private String description;
}
