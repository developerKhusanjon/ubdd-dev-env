package uz.ciasev.ubdd_service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "last_employment_info")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LastEmploymentInfo implements AdmEntity, Serializable {
    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.LAST_EMPLOYMENT_INFO;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "last_employment_info_id_seq")
    @SequenceGenerator(name = "last_employment_info_id_seq", sequenceName = "last_employment_info_id_seq", allocationSize = 1)
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
    private String place;

    @Getter
    @Setter
    private String placeInn;

    @Getter
    @Setter
    private String placeAddress;

    @Getter
    @Setter
    private Integer externalPositionId;

    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    private String payRate;

    @Getter
    @Setter
    private String contractNumber;

    @Getter
    @Setter
    private LocalDate contractDate;

    @Getter
    @Setter
    private String initiationDecreeNumber;

    @Getter
    @Setter
    private String terminationDecreeNumber;

    @Getter
    @Setter
    private LocalDate fromDate;

    @Getter
    @Setter
    private LocalDate toDate;

    @Getter
    @Setter
    private Boolean isEmployed;




    // JPA AND CRITERIA FIELDS

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;


    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }
}
