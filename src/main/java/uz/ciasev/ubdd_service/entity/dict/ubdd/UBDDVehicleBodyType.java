package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDVehicleBodyTypeDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_vehicle_body_type")
@NoArgsConstructor
@JsonDeserialize(using = UBDDVehicleBodyTypeDeserializer.class)
public class UBDDVehicleBodyType extends UbddAbstractDictEntity {
}
