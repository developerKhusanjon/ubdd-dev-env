package uz.ciasev.ubdd_service.entity.participant;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.ParticipantType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "participant")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"person", "admCase", "participantType"})
@EqualsAndHashCode(exclude = {"person", "admCase", "participantType"})
@EntityListeners(AuditingEntityListener.class)
public class Participant implements AdmEntity, Serializable {

    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.PARTICIPANT;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_id_seq")
    @SequenceGenerator(name = "participant_id_seq", sequenceName = "participant_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_type_id")
    private ParticipantType participantType;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    private String mobile;

    private String landline;

    @Column(name = "actual_address_id", insertable = false, updatable = false)
    private Long actualAddressId;

    private String photoUri;

    @Column(insertable = false, updatable = false)
    private Long mergedToParticipantId;


    //    JPA AND CRITERIA FIELDS

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "participant_type_id", insertable = false, updatable = false)
    private Long participantTypeId;


    public Participant(Person person, AdmCase admCase, ParticipantType participantType) {
        this.person = person;
        this.admCase = admCase;
        this.participantType = participantType;
    }

    public Long getActualAddressId() {
        if (actualAddress == null) return null;
        return actualAddress.getId();
    }

    public Long getAdmCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

    public Long getParticipantTypeId() {
        if (participantType == null) return null;
        return participantType.getId();
    }

    public Long getPersonId() {
        if (person == null) return null;
        return person.getId();
    }
}
