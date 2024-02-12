package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "court_file")
public class CourtFile {

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
    private Long caseId;

    @Getter
    private Long claimId;

    @Getter
    private Long externalId;

    @Getter
    private String uri;

    @Builder
    public CourtFile(Long caseId, Long claimId, Long externalId, String uri) {
        this.caseId = caseId;
        this.claimId = claimId;
        this.externalId = externalId;
        this.uri = uri;
    }

}