package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;

public interface SimpleEmiDictionaryService<T extends AbstractEmiDict>
        extends DictionaryCRUDService<T, EmiDictCreateRequestDTO<T>, EmiDictUpdateRequestDTO<T>> {
}
