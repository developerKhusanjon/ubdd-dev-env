package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.BackendDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;

public interface SimpleBackendDictionaryService<T extends AbstractBackendDict<A>, A extends Enum<A> & BackendAlias>
        extends AliasedDictionaryService<T, A>, UpdateDictionaryService<T, BackendDictUpdateRequestDTO<T>> {
}
