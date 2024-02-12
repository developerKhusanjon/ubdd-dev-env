package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDVehicleOwnerTypeCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDVehicleOwnerTypeUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDVehicleOwnerTypeDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_vehicle_owner_type")
@NoArgsConstructor
@JsonDeserialize(using = UBDDVehicleOwnerTypeDeserializer.class)
public class UBDDVehicleOwnerType extends UbddAbstractDictEntity {

    @Getter
    private Boolean isJuridic;

    public void construct(UBDDVehicleOwnerTypeCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(UBDDVehicleOwnerTypeUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(UBDDVehicleOwnerTypeUpdateDTOI request) {
        this.isJuridic = request.getIsJuridic();
    }
}
