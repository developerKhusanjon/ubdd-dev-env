package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiStatusDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;

public interface SimpleEmiStatusDictionaryService<T extends AbstractEmiStatusDict>
        extends DictionaryCRUDService<T, EmiStatusDictCreateRequestDTO<T>, EmiStatusDictUpdateRequestDTO<T>> {
}
