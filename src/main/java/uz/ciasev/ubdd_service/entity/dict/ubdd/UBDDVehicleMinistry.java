package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDVehicleMinistryDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_vehicle_ministry")
@NoArgsConstructor
@JsonDeserialize(using = UBDDVehicleMinistryDeserializer.class)
public class UBDDVehicleMinistry extends UbddAbstractDictEntity {
}
