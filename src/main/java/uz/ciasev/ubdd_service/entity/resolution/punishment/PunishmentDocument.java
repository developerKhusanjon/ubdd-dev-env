package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "punishment_document")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class PunishmentDocument implements AdmEntity {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.PUNISHMENT_DOCUMENT;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime editedTime;

    private String uri;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;
}
