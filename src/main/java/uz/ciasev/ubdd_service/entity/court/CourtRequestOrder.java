package uz.ciasev.ubdd_service.entity.court;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "court_request_order")
public class CourtRequestOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdTime;

    @Getter
    private Long caseId;

    @Getter
    private Long claimId;

    private Integer method;

    public CourtRequestOrder(CourtMethod method, Long caseId, Long claimId) {
        this.createdTime = LocalDateTime.now();
        this.caseId = caseId;
        this.claimId = claimId;
        this.method = method.getId();
    }


}
