package uz.ciasev.ubdd_service.entity.wanted;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "wanted_protocols_closed")
public class WantedProtocolClosed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wanted_protocol_id")
    private WantedProtocol wantedProtocol;

    @Column(name = "wanted_protocol_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long wantedProtocolId;

    private LocalDateTime closedTime = LocalDateTime.now();

    public WantedProtocolClosed(WantedProtocol wantedProtocol) {
        this.wantedProtocol = wantedProtocol;
    }
}
