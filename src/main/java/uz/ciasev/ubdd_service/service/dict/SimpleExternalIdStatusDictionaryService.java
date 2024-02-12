package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalStatusDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;

public interface SimpleExternalIdStatusDictionaryService<T extends AbstractExternalStatusDictEntity>
        extends DictionaryCRUDService<T, ExternalStatusDictCreateRequestDTO<T>, ExternalStatusDictUpdateRequestDTO<T>> {
}
