package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractAliasedDict;

public interface SimpleAliasedDictionaryService<T extends AbstractAliasedDict<A>, A extends Enum<A>>
        extends AliasedDictionaryService<T, A>, DictionaryCRUDService<T, EmiDictCreateRequestDTO<T>, EmiDictUpdateRequestDTO<T>> {
}
