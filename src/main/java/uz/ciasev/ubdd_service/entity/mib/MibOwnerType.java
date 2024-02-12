package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "mib_owner_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class MibOwnerType {

    @Getter
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private MibOwnerTypeAlias alias;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;
}
