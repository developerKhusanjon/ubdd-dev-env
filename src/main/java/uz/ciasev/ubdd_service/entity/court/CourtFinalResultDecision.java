package uz.ciasev.ubdd_service.entity.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.court.InstancesAliases;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Builder
@Table(name = "court_final_result_decision")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourtFinalResultDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    private Long caseId;
    private Long violatorId;
    private Long finalResult;
    @Enumerated(EnumType.STRING)
    private InstancesAliases instance;

    private Long cassationAdditionalResult;
    private Long cancelingReason;
    private Long changingReason;
}
