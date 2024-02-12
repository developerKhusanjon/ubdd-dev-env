package uz.ciasev.ubdd_service.entity.evidence;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "evidence")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class Evidence implements AdmEntity, Serializable {

    @Transient
    private EntityNameAlias entityNameAlias = EntityNameAlias.EVIDENCE;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evidence_id_seq")
    @SequenceGenerator(name = "evidence_id_seq", sequenceName = "evidence_id_seq", allocationSize = 1)
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
    private boolean isActive = true;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Setter
    @ManyToOne
    @JoinColumn(name = "evidence_category_id")
    private EvidenceCategory evidenceCategory;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_id")
    private Measures measure;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Setter
    @Getter
    private Long cost;

    @Setter
    @Getter
    private Double quantity;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private String evidencePhotoUri;

    @Setter
    @Getter
    private Integer evidenceSudId;

    // JPA AND CRITEIA FIELDS ONLY

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Column(name = "evidence_category_id", insertable = false, updatable = false)
    private Long evidenceCategoryId;

    @Column(name = "measure_id", insertable = false, updatable = false)
    private Long measureId;

    @Column(name = "currency_id", insertable = false, updatable = false)
    private Long currencyId;


    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getAdmCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

    public Long getPersonId() {
        if (person == null) return null;
        return person.getId();
    }

    public Long getEvidenceCategoryId() {
        if (evidenceCategory == null) return null;
        return evidenceCategory.getId();
    }

    public Long getMeasureId() {
        if (measure == null) return null;
        return measure.getId();
    }

    public Long getCurrencyId() {
        if (currency == null) return null;
        return currency.getId();
    }

}
