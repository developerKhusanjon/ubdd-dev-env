package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;

public interface SimpleExternalIdDictionaryService<T extends AbstractExternalDictEntity>
        extends DictionaryCRUDService<T, ExternalDictCreateRequestDTO<T>, ExternalDictUpdateRequestDTO<T>> {
}
