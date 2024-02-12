package uz.ciasev.ubdd_service.entity.court;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "court_decline_reasons_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CourtDeclineReasonsHistory {

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
    private LocalDate declinedDate;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<Long> declinedReasons;

    @Getter
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private AdmCase admCase;

    //  JPA AND CRITERIA FIELD ONLY

    @Column(name = "case_id", insertable = false, updatable = false)
    private Long caseId;


    public CourtDeclineReasonsHistory(AdmCase admCase, LocalDate declinedDate, List<Long> declinedReasons) {
        this.admCase = admCase;
        this.declinedDate = declinedDate;
        this.declinedReasons = declinedReasons;
        this.isActive = true;
    }

    public Long getCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

}
