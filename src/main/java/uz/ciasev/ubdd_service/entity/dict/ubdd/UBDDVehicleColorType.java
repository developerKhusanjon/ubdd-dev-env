package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDVehicleColorTypeDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_vehicle_color_type")
@NoArgsConstructor
@JsonDeserialize(using = UBDDVehicleColorTypeDeserializer.class)
public class UBDDVehicleColorType extends UbddAbstractDictEntity {
}
