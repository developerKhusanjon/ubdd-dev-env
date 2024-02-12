package uz.ciasev.ubdd_service.entity.victim;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.*;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.entity.dict.person.IntoxicationType;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "victim_detail")
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class VictimDetail implements AdmEntity, Serializable, ActorDetails, LastEmploymentInfoOwner {

    @Transient
    @Getter
    private final EntityNameAlias entityNameAlias = EntityNameAlias.VICTIM_DETAIL;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "victim_detail_id_seq")
    @SequenceGenerator(name = "victim_detail_id_seq", sequenceName = "victim_detail_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Getter
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @Getter
    private LocalDateTime editedTime;

    //    todo добаить это поле при миграцие
    //    private boolean isActive = true;

    @Getter
    @Setter
    private String signature;

    @Getter
    @Setter
    private String documentSeries;

    @Getter
    @Setter
    private String documentNumber;

    @Getter
    @Setter
    private LocalDate documentGivenDate;

    @Getter
    @Setter
    private LocalDate documentExpireDate;

    //private String photoUri;

    @Getter
    @Setter
    private String employmentPlace;

    @Getter
    @Setter
    private String employmentPosition;

    //    Данные внешней системы предоставившей инфорамцию о персоне

    @Getter
    @Setter
    private String externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "victim_id")
    @Setter
    @Getter
    private Victim victim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    @Setter
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    @Setter
    private PersonDocumentType personDocumentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_given_address_id")
    @Setter
    private Address documentGivenAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f1_address_id")
    @Setter
    private Address f1Address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_address_id")
    @Setter
    private Address residenceAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_category_id")
    @Setter
    private AgeCategory ageCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    @Setter
    private Occupation occupation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intoxication_type_id")
    @Setter
    private IntoxicationType intoxicationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_system_id")
    @Setter
    private ExternalSystem externalSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_employment_info_id")
    @Setter
    private LastEmploymentInfo lastEmploymentInfo;


//    JPA AND CRITERIA FIELD ONLY
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "victim_id", insertable = false, updatable = false)
    private Long victimId;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;

    @Column(name = "document_type_id", insertable = false, updatable = false)
    private Long personDocumentTypeId;

    @Column(name = "document_given_address_id", insertable = false, updatable = false)
    private Long documentGivenAddressId;

    @Column(name = "f1_address_id", insertable = false, updatable = false)
    private Long f1AddressId;

    @Column(name = "residence_address_id", insertable = false, updatable = false)
    private Long residenceAddressId;

    @Column(name = "age_category_id", insertable = false, updatable = false)
    private Long ageCategoryId;

    @Column(name = "occupation_id", insertable = false, updatable = false)
    private Long occupationId;

    @Column(name = "last_employment_info_id", insertable = false, updatable = false)
    private Long lastEmploymentInfoId;

    @Column(name = "intoxication_type_id", insertable = false, updatable = false)
    private Long intoxicationTypeId;

    @Column(name = "external_system_id", insertable = false, updatable = false)
    private Long externalSystemId;


    public Long getUserId() {
        if (this.user == null) return null;
        return user.getId();
    }

    public Long getVictimId() {
        if (this.victim == null) return null;
        return victim.getId();
    }

    public Long getProtocolId() {
        if (this.protocol == null) return null;
        return protocol.getId();
    }

    public Long getPersonDocumentTypeId() {
        if (this.personDocumentType == null) return null;
        return personDocumentType.getId();
    }

    public Long getDocumentGivenAddressId() {
        if (this.documentGivenAddress == null) return null;
        return documentGivenAddress.getId();
    }

    public Long getF1AddressId() {
        if (this.f1Address == null) return null;
        return f1Address.getId();
    }

    public Long getResidenceAddressId() {
        if (this.residenceAddress == null) return null;
        return residenceAddress.getId();
    }

    public Long getAgeCategoryId() {
        if (this.ageCategory == null) return null;
        return ageCategory.getId();
    }

    public Long getOccupationId() {
        if (this.occupation == null) return null;
        return occupation.getId();
    }

    @Override
    public Long getLastEmploymentInfoId() {
        if (this.lastEmploymentInfo == null) return null;
        return lastEmploymentInfo.getId();
    }

    public Long getIntoxicationTypeId() {
        if (this.intoxicationType == null) return null;
        return intoxicationType.getId();
    }

    public Long getExternalSystemId() {
        if (this.externalSystem == null) return null;
        return externalSystem.getId();
    }
}
