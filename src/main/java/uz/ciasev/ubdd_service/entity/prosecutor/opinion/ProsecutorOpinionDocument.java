package uz.ciasev.ubdd_service.entity.prosecutor.opinion;

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
@Table(name = "prosecutor_opinion_document")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProsecutorOpinionDocument extends AbstractProsecutorDocument {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.PROSECUTOR_PROTEST_DOCUMENT;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prosecutor_opinion_document_id_seq")
    @SequenceGenerator(name = "prosecutor_opinion_document_id_seq", sequenceName = "prosecutor_opinion_document_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opinion_id")
    @Setter
    private ProsecutorOpinion opinion;

    public ProsecutorOpinionDocument(User user, ProsecutorOpinion opinion, String uri) {
        super(user, uri);
        this.opinion = opinion;
    }

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "opinion_id", insertable = false, updatable = false)
    private Long opinionId;

    public Long getOpinionId() {
        if (this.opinion == null) {
            return null;
        }
        return this.opinion.getId();
    }
}
