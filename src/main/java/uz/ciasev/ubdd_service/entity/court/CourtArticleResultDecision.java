package uz.ciasev.ubdd_service.entity.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Builder
@Table(name = "court_article_result_decision")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourtArticleResultDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "court_final_result_decision_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourtFinalResultDecision finalResult;

    private Long articleId;
    private Long articlePartId;
    private Long articleResult;
    private Long reArticleId;
    private Long reArticlePartId;
}
