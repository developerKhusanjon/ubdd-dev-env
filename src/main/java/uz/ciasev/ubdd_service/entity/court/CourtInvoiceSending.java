package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.utils.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "court_invoice_sending")
@EntityListeners(AuditingEntityListener.class)
public class CourtInvoiceSending {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = true)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Column(updatable = false)
    private Boolean isUpdate;

    @Getter
    @Column(updatable = false)
    private Long resolutionId;

    @Getter
    @Column(updatable = false)
    private Long typeId;

    @Getter
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private CourtInvoiceType type;

    @Column(updatable = false)
    private Boolean isOnlyMoveOld;

    @Getter
    private Boolean isSent;

    @Getter
    private String comment;

    @Getter
    private String error;

    @Getter
    private Boolean hasError;

    public CourtInvoiceSending(Long resolutionId, CourtInvoiceType type, Long typeId, Boolean isOnlyMoveOld) {
        this.resolutionId = resolutionId;
        this.type = type;
        this.typeId = typeId;
        this.isOnlyMoveOld = isOnlyMoveOld;

        this.isSent = false;
        this.isUpdate = false;
        this.hasError = false;
        this.error = null;
    }

    public boolean isOnlyMoveOld() {
        return Optional.ofNullable(isOnlyMoveOld).orElse(false);
    }

    public void sentWithError(String error) {
        this.isSent = true;

        this.hasError = true;
        this.error = error;
    }

    public void sentSuccess() {
        this.isSent = true;

        this.hasError = false;
    }

    public void addComment(String newComment) {
        if (comment == null) {
            comment = "";
        }

        comment = StringUtils.tail(comment + newComment, 200);
    }
}
