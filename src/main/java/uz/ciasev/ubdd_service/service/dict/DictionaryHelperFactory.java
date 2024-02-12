package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

public interface DictionaryHelperFactory {

    <T extends AbstractDict > DictionaryHelperForList<T> constructHelperForList(DictionaryServiceWithRepository<T> owner);

    <T extends AbstractDict, D extends DictCreateRequest<T>> DictionaryHelperForCreate<T, D> constructHelperForCreate(DictionaryServiceWithRepository<T> owner);

    <T extends AbstractDict, U extends DictUpdateRequest<T>> DictionaryHelperForUpdate<T, U> constructHelperForUpdate(DictionaryServiceWithRepository<T> owner);

    <T extends AbstractDict> DictionaryHelperForActivity<T> constructHelperForActivity(DictionaryServiceWithRepository<T> owner);
}
