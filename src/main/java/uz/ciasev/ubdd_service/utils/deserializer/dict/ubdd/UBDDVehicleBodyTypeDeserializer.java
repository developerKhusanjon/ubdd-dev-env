package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDVehicleBodyTypeDeserializer extends AbstractDictDeserializer<UBDDVehicleBodyType> {

    @Autowired
    public UBDDVehicleBodyTypeDeserializer(DictionaryService<UBDDVehicleBodyType> vehicleBodyTypeService) {
        super(UBDDVehicleBodyType.class, vehicleBodyTypeService);
    }
}