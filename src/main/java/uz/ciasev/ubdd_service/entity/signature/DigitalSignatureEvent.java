package uz.ciasev.ubdd_service.entity.signature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_signature_events")
@AllArgsConstructor
@NoArgsConstructor
public class DigitalSignatureEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private LocalDateTime createdTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private SignatureEvent eventType;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityType;

    @Getter
    @Setter
    private Long entityId;

    @Getter
    @Setter
    private String signature;

    @Getter
    @Setter
    private String signedData;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    @Getter
    @Setter
    private DigitalSignatureCertificate certificate;

    @Column(name = "certificate_id", insertable = false, updatable = false)
    private Long certificateId;
}
