package uz.ciasev.ubdd_service.entity.history;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "h_edit_protocol_vehicle_number_registration")
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditProtocolVehicleNumberRegistration extends Registration {

    private Long protocolId;

    private String fromVehicleNumber;

    private String toVehicleNumber;

    //private String changeReason;
    @ManyToOne
    @JoinColumn(name = "adm_case_change_reason_id")
    private ChangeReasonType changeReason;
}
