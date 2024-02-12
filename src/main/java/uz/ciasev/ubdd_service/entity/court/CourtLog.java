package uz.ciasev.ubdd_service.entity.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Immutable
@Data
@Entity
@Table(name = "court_log")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourtLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "court_log_id_seq")
    @SequenceGenerator(name = "court_log_id_seq", sequenceName = "court_log_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime editedTime;

    private Long caseId;
    private Long claimId;
    private String method;
    private String data;
}
