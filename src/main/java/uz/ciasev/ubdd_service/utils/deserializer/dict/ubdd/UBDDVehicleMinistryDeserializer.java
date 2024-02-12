package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleMinistry;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDVehicleMinistryDeserializer extends AbstractDictDeserializer<UBDDVehicleMinistry> {

    @Autowired
    public UBDDVehicleMinistryDeserializer(DictionaryService<UBDDVehicleMinistry> vehicleMinistryService) {
        super(UBDDVehicleMinistry.class, vehicleMinistryService);
    }
}