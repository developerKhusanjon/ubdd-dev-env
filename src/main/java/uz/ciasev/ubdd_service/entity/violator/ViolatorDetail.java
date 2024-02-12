package uz.ciasev.ubdd_service.entity.violator;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
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
import java.util.Set;

@Entity
@Table(name = "violator_detail")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ViolatorDetail implements AdmEntity, Serializable, ActorDetails, LastEmploymentInfoOwner {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.VIOLATOR_DETAIL;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "violator_detail_id_seq")
    @SequenceGenerator(name = "violator_detail_id_seq", sequenceName = "violator_detail_id_seq", allocationSize = 1)
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    private String signature;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "violator_id")
    private Violator violator;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private PersonDocumentType personDocumentType;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_given_address_id")
    private Address documentGivenAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f1_address_id")
    private Address f1Address;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_address_id")
    private Address residenceAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_category_id")
    private AgeCategory ageCategory;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intoxication_type_id")
    private IntoxicationType intoxicationType;

    @Getter
    @Setter
    private String employmentPlace;

    @Getter
    @Setter
    private String employmentPosition;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_employment_info_id")
    private LastEmploymentInfo lastEmploymentInfo;

    @Getter
    @Setter
    private String additionally;

    // Данные внешней системы предоставившей инфорамцию о персоне

    @Getter
    @Setter
    private String externalId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_system_id")
    private ExternalSystem externalSystem;


    // JPA AND CRITERIA FIELDS

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "violator_id", insertable = false, updatable = false)
    private Long violatorId;

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

    @Column(name = "intoxication_type_id", insertable = false, updatable = false)
    private Long intoxicationTypeId;

    @Column(name = "external_system_id", insertable = false, updatable = false)
    private Long externalSystemId;

    @Column(name = "last_employment_info_id", insertable = false, updatable = false)
    private Long lastEmploymentInfoId;

    @OneToMany(mappedBy = "violatorDetail", fetch = FetchType.LAZY)
    private Set<Protocol> protocols;

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getViolatorId() {
        if (violator == null) return null;
        return violator.getId();
    }

    public Long getPersonDocumentTypeId() {
        if (personDocumentType == null) return null;
        return personDocumentType.getId();
    }

    public Long getDocumentGivenAddressId() {
        if (documentGivenAddress == null) return null;
        return documentGivenAddress.getId();
    }

    public Long getF1AddressId() {
        if (f1Address == null) return null;
        return f1Address.getId();
    }

    public Long getResidenceAddressId() {
        if (residenceAddress == null) return null;
        return residenceAddress.getId();
    }

    public Long getAgeCategoryId() {
        if (ageCategory == null) return null;
        return ageCategory.getId();
    }

    public Long getOccupationId() {
        if (occupation == null) return null;
        return occupation.getId();
    }

    @Override
    public Long getLastEmploymentInfoId() {
        if (lastEmploymentInfo == null) return null;
        return lastEmploymentInfo.getId();
    }

    public Long getIntoxicationTypeId() {
        if (intoxicationType == null) return null;
        return intoxicationType.getId();
    }

    public Long getExternalSystemId() {
        if (externalSystem == null) return null;
        return externalSystem.getId();
    }

}
