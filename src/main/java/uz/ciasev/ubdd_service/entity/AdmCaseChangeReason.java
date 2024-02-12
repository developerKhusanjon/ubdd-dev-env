package uz.ciasev.ubdd_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Entity
@Getter
@Setter
@Table(name = "adm_case_change_reason")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AdmCaseChangeReason implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityName;
    private Long entityId;

    @Column(name = "change_reason_id", insertable = false, updatable = false)
    private Long changeReasonTypeId;

    @Column(name = "document_reason_url")
    private String documentUrl;

    @Column(name = "reason_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @ManyToOne
    @JoinColumn(name = "change_reason_id")
    private ChangeReasonType changeReasonType;
}
