package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;

public interface UnknownValueByAliasDictionaryService<T extends AliasedDictEntity<A>, A extends Enum<A>>
        extends AliasedDictionaryService<T, A>, UnknownValueDictionaryService<T> {

    A getUnknownAlias();

    default T getUnknown() {
        return getByAlias(getUnknownAlias());
    }
}
