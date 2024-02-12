package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "billing_sending_error_log")
@EntityListeners(AuditingEntityListener.class)
public class BillingSendingErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private Long billingSendingId;

    private String error;

    public BillingSendingErrorLog(BillingSending billingSending, Exception error) {
        this.billingSendingId = billingSending.getId();
        this.error = String.format("%s: %s", error.getClass().getSimpleName(), error.getMessage());
    }
}
