package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleColorType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDVehicleColorTypeDeserializer extends AbstractDictDeserializer<UBDDVehicleColorType> {

    @Autowired
    public UBDDVehicleColorTypeDeserializer(DictionaryService<UBDDVehicleColorType> vehicleColorTypeService) {
        super(UBDDVehicleColorType.class, vehicleColorTypeService);
    }
}