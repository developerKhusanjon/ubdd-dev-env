package uz.ciasev.ubdd_service.entity.dict.ubdd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.entity.dict.requests.VehicleNumberTypeDTOI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_vehicle_number_type")
@NoArgsConstructor
public class VehicleNumberType extends AbstractBackendDict<VehicleNumberTypeAlias> {

    @Getter
    private Boolean isNeedSendToMail;

    @Getter
    @Column(name = "is_need_send_to_mid")
    // Минестерство иностранных дел
    private Boolean isNeedSendToMID;

    @Getter
    @Column(name = "is_need_move_to_vai")
    // Военная автомобильная инспекция
    private Boolean isNeedMoveToVAI;

    public void update(VehicleNumberTypeDTOI request) {
        this.isNeedSendToMail = request.getIsNeedSendToMail();
        this.isNeedSendToMID = request.getIsNeedSendToMID();
        this.isNeedMoveToVAI = request.getIsNeedMoveToVAI();
    }
}
