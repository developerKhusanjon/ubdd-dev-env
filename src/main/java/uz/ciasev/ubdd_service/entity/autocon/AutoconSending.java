package uz.ciasev.ubdd_service.entity.autocon;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.utils.converter.AutoconSendingStatusIdToAliasConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "autocon_sending")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AutoconSending {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Getter
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime editedTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punishment_id", updatable = false)
    private Punishment punishment;

    @Getter
    @Setter
    @Convert(converter = AutoconSendingStatusIdToAliasConverter.class)
    @Column(name = "status_id")
    private AutoconSendingStatusAlias status;

    @Getter
    @Setter
    private LocalDateTime openedTime;

    @Getter
    @Setter
    private LocalDateTime closedTime;

    @Getter
    @Setter
    private LocalDateTime lastProcessTime;

    @Getter
    @Setter
    private Boolean isLastProcessError;

    @Getter
    @Setter
    private String lastErrorText;

    @Getter
    @Setter
    @Builder.Default
    private Long processCount = 0L;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "punishment_id", insertable = false, updatable = false)
    private Long punishmentId;

    public Long getPunishmentId() {
        if (punishment == null) return null;
        return punishment.getId();
    }

    public void incrementProcessCount() {
        this.processCount++;
    }

    public static AutoconSending of(Punishment punishment) {
        return AutoconSending.builder()
                .punishment(punishment)
                .status(AutoconSendingStatusAlias.AWAIT_OPEN)
                .processCount(0L)
                .build();
    }
}
