package uz.ciasev.ubdd_service.service.dict.ubdd;

import uz.ciasev.ubdd_service.dto.internal.dict.request.VehicleNumberTypeRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberTypeAlias;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.UpdateDictionaryService;

public interface VehicleNumberTypeService extends AliasedDictionaryService<VehicleNumberType, VehicleNumberTypeAlias>, UpdateDictionaryService<VehicleNumberType, VehicleNumberTypeRequestDTO> {
}
