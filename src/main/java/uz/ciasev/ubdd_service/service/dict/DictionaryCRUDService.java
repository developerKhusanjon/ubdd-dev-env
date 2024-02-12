package uz.ciasev.ubdd_service.service.dict;

public interface DictionaryCRUDService<T, C, U> extends
        DictionaryService<T>,
        ActivityDictionaryService<T>,
        CreateDictionaryService<T, C>,
        UpdateDictionaryService<T, U> {
}
