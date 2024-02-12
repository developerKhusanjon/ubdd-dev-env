package uz.ciasev.ubdd_service.entity.prosecutor.protest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutorDocument;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "prosecutor_protest_document")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProsecutorProtestDocument extends AbstractProsecutorDocument {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.PROSECUTOR_PROTEST_DOCUMENT;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prosecutor_protest_document_id_seq")
    @SequenceGenerator(name = "prosecutor_protest_document_id_seq", sequenceName = "prosecutor_protest_document_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protest_id")
    @Setter
    private ProsecutorProtest protest;

    public ProsecutorProtestDocument(User user, ProsecutorProtest protest, String uri) {
        super(user, uri);
        this.protest = protest;
    }

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "protest_id", insertable = false, updatable = false)
    private Long protestId;

    public Long getProtestId() {
        if (this.protest == null) {
            return null;
        }
        return this.protest.getId();
    }

}
