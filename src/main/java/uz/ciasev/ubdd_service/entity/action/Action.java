package uz.ciasev.ubdd_service.entity.action;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.utils.converter.ActionAliasConverter;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "action")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Action implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Convert(converter = ActionAliasConverter.class)
    private ActionAlias alias;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;
}
