package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.BackendStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;

public interface SimpleBackendStatusDictionaryService<T extends AbstractBackendStatusDict<A>, A extends Enum<A> & BackendAlias>
        extends AliasedDictionaryService<T, A>, UpdateDictionaryService<T, BackendStatusDictUpdateRequestDTO<T>> {
}
