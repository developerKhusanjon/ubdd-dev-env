package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDImpoundCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDImpoundUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDImpoundDeserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_impound")
@NoArgsConstructor
@JsonDeserialize(using = UBDDImpoundDeserializer.class)
public class UBDDImpound extends UbddAbstractDictEntity {

    @Getter
    @Column(name = "d_vehicle_arrest_place_id")
    private Long vehicleArrestPlaceId;

    public void construct(UBDDImpoundCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(UBDDImpoundUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(UBDDImpoundUpdateDTOI request) {
        this.vehicleArrestPlaceId = request.getVehicleArrestPlaceId();
    }
}
