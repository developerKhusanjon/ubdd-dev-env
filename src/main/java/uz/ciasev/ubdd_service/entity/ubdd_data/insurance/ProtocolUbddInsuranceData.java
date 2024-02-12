package uz.ciasev.ubdd_service.entity.ubdd_data.insurance;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import javax.persistence.*;

@Entity
@Table(name = "v_protocol_ubdd_insurance_data")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProtocolUbddInsuranceData extends UbddInsuranceDataAbstract {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;

}
