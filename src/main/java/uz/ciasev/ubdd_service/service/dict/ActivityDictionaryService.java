package uz.ciasev.ubdd_service.service.dict;

public interface ActivityDictionaryService<T> extends DictionaryService<T> {

    void close(Long id);

    void open(Long id);
}
