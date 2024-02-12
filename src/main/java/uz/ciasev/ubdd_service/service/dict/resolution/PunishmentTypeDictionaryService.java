package uz.ciasev.ubdd_service.service.dict.resolution;

import uz.ciasev.ubdd_service.dto.internal.dict.request.PunishmentTypeRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.UpdateDictionaryService;

public interface PunishmentTypeDictionaryService extends AliasedDictionaryService<PunishmentType, PunishmentTypeAlias>, UpdateDictionaryService<PunishmentType, PunishmentTypeRequestDTO> {
}
