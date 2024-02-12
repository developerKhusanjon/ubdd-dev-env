package uz.ciasev.ubdd_service.service.dict;

import java.util.Optional;

public interface AliasedDictionaryService<T, A extends Enum> extends DictionaryService<T> {

    Class<A>  getAliasClass();

    Optional<T> findByAlias(A id);

//    default Class<A> getAliasClass() {return null;};
//
//    default Optional<T> findByAlias(A id) {return null;};

    T getByAlias(A id);
}
