package uz.ciasev.ubdd_service.entity.invoice;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "invoice_owner_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class InvoiceOwnerType {


    @Getter
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private InvoiceOwnerTypeAlias alias;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;

    public boolean is(InvoiceOwnerTypeAlias o) {
        return o.equals(alias);
    }
}
