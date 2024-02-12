package uz.ciasev.ubdd_service.entity.victim;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Entity
@Table(name = "victim")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class Victim implements AdmEntity, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "victim_id_seq")
    @SequenceGenerator(name = "victim_id_seq", sequenceName = "victim_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Getter
    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Getter
    @Column(insertable = false, updatable = false)
    private Long mergedToVictimId;

    @Getter
    @Column(name = "actual_address_id", insertable = false, updatable = false)
    private Long actualAddressId;

    @Getter
    @Column(name = "post_address_id", insertable = false, updatable = false)
    private Long postAddressId;

    @Getter
    @Setter
    private String inn;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private String landline;

    @Getter
    @Setter
    private String photoUri;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;


    // поля ради метомоделей и репозиториев//

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_address_id")
    private Address postAddress;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Builder(toBuilder = true)
    public Victim(AdmCase admCase, Person person, Address actualAddress, Address postAddress, String inn, String mobile, String landline, String photoUri) {
        this.admCase = admCase;
        this.admCaseId = admCase.getId();

        this.person = person;
        this.personId = person.getId();

        this.actualAddress = actualAddress;
        this.actualAddressId = actualAddress.getId();

        this.postAddress = postAddress;
        this.postAddressId = postAddress.getId();

        this.inn = inn;
        this.mobile = mobile;
        this.landline = landline;
        this.photoUri = photoUri;

        this.id = null;
        this.mergedToVictimId = null;
        this.createdTime = null;
    }

    public EntityNameAlias getEntityNameAlias() {
        return EntityNameAlias.VICTIM;
    }

    public Long getPersonId() {
        return this.person.getId();
    }
}
