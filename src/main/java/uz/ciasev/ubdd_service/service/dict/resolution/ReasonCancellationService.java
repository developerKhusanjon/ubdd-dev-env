package uz.ciasev.ubdd_service.service.dict.resolution;

import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface ReasonCancellationService extends
        AliasedDictionaryService<ReasonCancellation, ReasonCancellationAlias>,
        DictionaryCRUDService<ReasonCancellation, EmiDictCreateRequestDTO<ReasonCancellation>, EmiDictUpdateRequestDTO<ReasonCancellation>> {
}
