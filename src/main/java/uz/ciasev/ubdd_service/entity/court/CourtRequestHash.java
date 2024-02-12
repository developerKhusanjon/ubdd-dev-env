package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "court_request_hash")
public class CourtRequestHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdTime;

    private Long method;

    private Long caseId;

    private Long claimId;

    private Integer hashCode;

    public CourtRequestHash(Long method, Long caseId, Long claimId, Integer hashCode) {
        this.createdTime = LocalDateTime.now();
        this.method = method;
        this.caseId = caseId;
        this.claimId = claimId;
        this.hashCode = hashCode;
    }


}
