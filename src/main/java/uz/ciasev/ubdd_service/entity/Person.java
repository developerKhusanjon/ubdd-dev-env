package uz.ciasev.ubdd_service.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.utils.FioUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class Person implements Serializable {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
    @SequenceGenerator(name = "person_id_seq", sequenceName = "person_id_seq", allocationSize = 1)
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
    private String pinpp;

    @Getter
    @Setter
    private boolean isRealPinpp;

    // Имя
    @Getter
    @Setter
    private String firstNameKir;

    // Отчество
    @Getter
    @Setter
    private String secondNameKir;

    // Фамилия
    @Getter
    @Setter
    private String lastNameKir;

    // Имя
    @Getter
    @Setter
    private String firstNameLat;

    // Отчество
    @Getter
    @Setter
    private String secondNameLat;

    // Фамилия
    @Getter
    @Setter
    private String lastNameLat;

    @Getter
    @Setter
    private LocalDate birthDate;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_address_id")
    private Address birthAddress;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "citizenship_type_id")
    private CitizenshipType citizenshipType;


    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "birth_address_id", insertable = false, updatable = false)
    private Long birthAddressId;

    @Column(name = "gender_id", insertable = false, updatable = false)
    private Long genderId;

    @Column(name = "citizenship_type_id", insertable = false, updatable = false)
    private Long citizenshipTypeId;

    @Column(name = "nationality_id", insertable = false, updatable = false)
    private Long nationalityId;

    public Long getBirthAddressId() {
        if (birthAddress == null) return null;
        return birthAddress.getId();
    }

    public Long getGenderId() {
        if (gender == null) return null;
        return gender.getId();
    }

    public Long getCitizenshipTypeId() {
        if (citizenshipType == null) return null;
        return citizenshipType.getId();
    }

    public Long getNationalityId() {
        if (nationality == null) return null;
        return nationality.getId();
    }


    public Person(Long id) {
        this.id = id;
    }

    public String getFIOLat() {
        return FioUtils.buildFullFio(
                firstNameLat,
                secondNameLat,
                lastNameLat
        );
    }

    public String getShortFioLat() {
        return FioUtils.buildShortFio(
            firstNameLat,
            secondNameLat,
            lastNameLat
        );
    }
}
