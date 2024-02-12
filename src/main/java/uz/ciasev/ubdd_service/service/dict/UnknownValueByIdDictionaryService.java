package uz.ciasev.ubdd_service.service.dict;

public interface UnknownValueByIdDictionaryService<T> extends DictionaryService<T>, UnknownValueDictionaryService<T> {

    Long getUnknownId();

    default T getUnknown() {
        return getById(getUnknownId());
    }
}
