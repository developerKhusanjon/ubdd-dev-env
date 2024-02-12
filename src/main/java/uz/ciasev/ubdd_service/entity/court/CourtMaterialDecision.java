package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "court_material_decision")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourtMaterialDecision {

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
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_material_id")
    private CourtMaterial courtMaterial;

    @Getter
    @Setter
    private Long defendantId;

    @Getter
    @Setter
    private boolean isParticipated;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prosecutor_region_id")
    private Region prosecutorRegion;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prosecutor_district_id")
    private District prosecutorDistrict;

    // CRITERIA AND JPA ONLY FIELDS

    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    @Column(name = "court_material_id", insertable = false, updatable = false)
    private Long courtMaterialId;

    public Long getDecisionId() {
        if (decision == null) return null;
        return decision.getId();
    }

    public Long getCourtMaterialId() {
        if (courtMaterial == null) return null;
        return courtMaterial.getId();
    }


    public CourtMaterialDecision(Decision decision, CourtMaterial material) {
        this.decision = decision;
        this.courtMaterial = material;
        this.isParticipated = false;
    }
}