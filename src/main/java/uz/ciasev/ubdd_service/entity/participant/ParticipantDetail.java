package uz.ciasev.ubdd_service.entity.participant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.ActorDetails;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Getter
@Setter
@Entity
@Table(name = "participant_detail")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class ParticipantDetail implements AdmEntity, Serializable, ActorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_detail_id_seq")
    @SequenceGenerator(name = "participant_detail_id_seq", sequenceName = "participant_detail_id_seq", allocationSize = 1)
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

    //    todo добаить это поле при миграцие
    //    private boolean isActive = true;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    private String signature;

    @Column(name = "participant_id", insertable = false, updatable = false)
    private Long participantId;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;

    @Column(name = "document_type_id", insertable = false, updatable = false)
    private Long personDocumentTypeId;

    private String documentSeries;

    private String documentNumber;

    @Column(name = "document_given_address_id", insertable = false, updatable = false)
    private Long documentGivenAddressId;

    private LocalDate documentGivenDate;

    private LocalDate documentExpireDate;

    @Column(name = "f1_address_id", insertable = false, updatable = false)
    private Long f1AddressId;

    @Column(name = "residence_address_id", insertable = false, updatable = false)
    private Long residenceAddressId;

    @Column(name = "age_category_id", insertable = false, updatable = false)
    private Long ageCategoryId;

    //private String photoUri;

//    Данные внешней системы предоставившей инфорамцию о персоне

    private String externalId;

    @Column(name = "external_system_id", insertable = false, updatable = false)
    private Long externalSystemId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private PersonDocumentType personDocumentType;

    @ManyToOne
    @JoinColumn(name = "document_given_address_id")
    private Address documentGivenAddress;

    @ManyToOne
    @JoinColumn(name = "f1_address_id")
    private Address f1Address;

    @ManyToOne
    @JoinColumn(name = "residence_address_id")
    private Address residenceAddress;

    @ManyToOne
    @JoinColumn(name = "age_category_id")
    private AgeCategory ageCategory;

    @ManyToOne
    @JoinColumn(name = "external_system_id")
    private ExternalSystem externalSystem;

    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.PARTICIPANT_DETAIL;
}
