package uz.ciasev.ubdd_service.entity.autocon;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;
import uz.ciasev.ubdd_service.utils.converter.AutoconSendingStatusIdToAliasConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "autocon_sending_status_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoconSendingStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Builder.Default
    @Getter
    private LocalDateTime createdTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "autocon_sending_id")
    @Getter
    private AutoconSending autoconSending;

    @Column(name = "autocon_sending_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Long autoconSendingId;

    @Getter
    @Convert(converter = AutoconSendingStatusIdToAliasConverter.class)
    @Column(name = "status_id")
    private AutoconSendingStatusAlias status;

    public static AutoconSendingStatusLog of(AutoconSending autoconSending) {

        return AutoconSendingStatusLog.builder()
                .autoconSending(autoconSending)
                .status(autoconSending.getStatus())
                .build();
    }
}
