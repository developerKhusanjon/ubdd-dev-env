package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import javax.persistence.*;

@Entity
@Table(name = "v_protocol_ubdd_tex_pass_data")
@NoArgsConstructor
public class ProtocolUbddTexPassData extends UbddTexPassDataAbstract {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;
}
