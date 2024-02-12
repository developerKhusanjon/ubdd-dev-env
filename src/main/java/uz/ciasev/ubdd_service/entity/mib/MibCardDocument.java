package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.SlaveAdmEntity;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "mib_card_document")
@NoArgsConstructor
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class MibCardDocument implements SlaveAdmEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.MIB_CARD_DOCUMENT;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mib_card_document_id_seq")
    @SequenceGenerator(name = "mib_card_document_id_seq", sequenceName = "mib_card_document_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private String uri;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "card_id")
    private MibExecutionCard card;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;


    //  JPA AND CRITERIA FIELD ONLY

    @Column(name = "card_id", insertable = false, updatable = false)
    private Long cardId;


    @Override
    public AdmEntity getMaster() {
        return card;
    }

    public Long getCardId() {
        if (card == null) return null;
        return card.getId();

    }

    public Long getDocumentTypeId() {
        if (documentType == null) return null;
        return documentType.getId();

    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();

    }
}