package uz.ciasev.ubdd_service.service.dict.ubdd;

import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDVehicleOwnerTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDVehicleOwnerTypeUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByIdDictionaryService;

public interface UBDDVehicleOwnerTypeService extends DictionaryCRUDService<UBDDVehicleOwnerType, UBDDVehicleOwnerTypeCreateRequestDTO, UBDDVehicleOwnerTypeUpdateRequestDTO>, UnknownValueByIdDictionaryService<UBDDVehicleOwnerType> {

}
