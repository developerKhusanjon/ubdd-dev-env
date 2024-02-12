package uz.ciasev.ubdd_service.entity.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Document implements AdmEntity, Serializable {

    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.DOCUMENT;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_seq")
    @SequenceGenerator(name = "document_id_seq", sequenceName = "document_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime editedTime;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Column(name = "document_type_id", insertable = false, updatable = false)
    private Long documentTypeId;

    @Column(name = "file_format_id", insertable = false, updatable = false)
    private Long fileFormatId;

    private String description;

    private String url;

    private String extension;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AdmCase admCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "file_format_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FileFormat fileFormat;
}