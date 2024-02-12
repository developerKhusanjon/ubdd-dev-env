package uz.ciasev.ubdd_service.entity.mib;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mib_card_movement_return_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MibCardMovementReturnRequest implements AdmEntity {

    @Transient
    @Getter
    private final EntityNameAlias entityNameAlias = EntityNameAlias.MIB_CARD_MOVEMENT_RETURN_REQUEST;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mib_card_movement_return_request_id_seq")
    @SequenceGenerator(name = "mib_card_movement_return_request_id_seq", sequenceName = "mib_card_movement_return_request_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
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
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "movement_id", updatable = false)
    private MibCardMovement movement;

    @Getter
    @ManyToOne
    @JoinColumn(name = "reason_id", updatable = false)
    private MibReturnRequestReason reason;

    @Getter
    @Column(updatable = false)
    private String comment;

    @Getter
    @Column(name = "send_status_id")
    private Long sendStatusId;

    @Getter
    private String sendMessage;

    @Getter
    private Boolean isApiErrorHappened;

    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Column(name = "movement_id", updatable = false, insertable = false)
    private Long movementId;

    @Column(name = "reason_id", updatable = false, insertable = false)
    private Long reasonId;


    public MibCardMovementReturnRequest(User user, MibCardMovement movement, CreateRequest request) {
        this.user = user;
        this.movement = movement;
        this.reason = request.reason;
        this.comment = request.comment;
    }

    public void setSendResult(boolean apiError, Long sendStatusId, String sendMessage) {
        this.isApiErrorHappened = apiError;
        this.sendStatusId = sendStatusId;
        this.sendMessage = sendMessage;
    }

    public Long getUserId() {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public Long getMovementId() {
        if (movement == null) {
            return null;
        }
        return movement.getId();
    }

    public Long getReasonId() {
        if (reason == null) {
            return null;
        }
        return reason.getId();
    }

    @Data
    public static class CreateRequest {
        private MibReturnRequestReason reason;
        private String comment;
    }

}
