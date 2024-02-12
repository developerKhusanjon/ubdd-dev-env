package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mib_auto_send_log")
@NoArgsConstructor
public class MibAutoSendLog {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sendTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "card_id")
    private MibExecutionCard card;

    private String error;

    public MibAutoSendLog(MibExecutionCard card) {
        this.card = card;
    }
}
