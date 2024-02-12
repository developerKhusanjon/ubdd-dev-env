package uz.ciasev.ubdd_service.entity.court;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "court_adm_case_movement")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourtAdmCaseMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private Long caseId;

    private Long claimId;

    @Column(name = "court_status_id")
    private Long statusId;

    private LocalDateTime statusTime;

    @Enumerated(EnumType.STRING)
    private CourtMovementAlias alias;

    private String validationErrors;

    private Boolean isSentToCourt;
}
