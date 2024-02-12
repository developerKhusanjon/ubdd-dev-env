package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "court_material")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class CourtMaterial {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Builder.Default
    private boolean isActive = true;

    @Setter
    @JoinColumn(name = "user_id", updatable = false)
    private Long userId;

    @Getter
    @Setter
    private Long claimId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    public Long getStatusId() {
        if (status == null) return null;
        return this.status.getId();
    }
}