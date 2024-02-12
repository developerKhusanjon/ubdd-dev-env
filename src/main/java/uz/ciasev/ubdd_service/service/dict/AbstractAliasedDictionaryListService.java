package uz.ciasev.ubdd_service.service.dict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.dict.AbstractAliasedDictRepository;

import java.util.Map;
import java.util.Optional;

@Service
public abstract class AbstractAliasedDictionaryListService<T extends AbstractDict & AliasedDictEntity<A>, A extends Enum<A>>
        implements DictionaryServiceWithRepository<T>, AliasedDictionaryService<T, A> {

    protected final DictionaryHelperForList<T> listHelper;

    protected AbstractAliasedDictionaryListService() {
        this.listHelper = new DictionaryHelperForList<>(this);
    }

    @Override
    abstract public AbstractAliasedDictRepository<T, A> getRepository();

    @Override
    public Object buildListResponseDTO(T entity) {
        return new AliasedDictResponseDTO(entity);
    }

    @Override
    public Page<T> findAll(Map<String, String> filters, Pageable pageable) {
        return listHelper.findAll(filters, pageable);
    }

    @Override
    public Optional<T> findByAlias(A alias) {
        return getRepository().findByAlias(alias);
    }

    @Override
    public T getByAlias(A alias) {
        return findByAlias(alias)
                .orElseThrow(() -> new EntityByAliasNotPresent(getEntityClass(), alias.name()));
    }
}
