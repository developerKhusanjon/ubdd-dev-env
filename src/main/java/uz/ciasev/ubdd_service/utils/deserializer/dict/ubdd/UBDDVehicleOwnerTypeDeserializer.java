package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDVehicleOwnerTypeDeserializer extends AbstractDictDeserializer<UBDDVehicleOwnerType> {

    @Autowired
    public UBDDVehicleOwnerTypeDeserializer(DictionaryService<UBDDVehicleOwnerType> vehicleOwnerService) {
        super(UBDDVehicleOwnerType.class, vehicleOwnerService);
    }
}