package uz.ciasev.ubdd_service.service.dict;

public interface ExternalIdSavingDictionaryService<T> extends DictionaryService<T> {

    T createNewWithId(Long id);

    default T getOrCreateNew(Long id) {
        return findById(id).orElseGet(() -> createNewWithId(id));
    }

}
