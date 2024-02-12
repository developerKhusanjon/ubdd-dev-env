package uz.ciasev.ubdd_service.service.dict;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.dict.AbstractAliasedDictRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class AbstractAliasedDictionaryCRUDService<T extends AbstractDict & AliasedDictEntity<A>, A extends Enum<A>, D extends DictCreateRequest<T>, U extends DictUpdateRequest<T>>
        implements DictionaryServiceWithRepository<T>,
        AliasedDictionaryService<T, A>,
        DictionaryCRUDService<T, D, U> {

    protected DictionaryHelperForList<T> listHelper;
    protected DictionaryHelperForCreate<T, D> createDictHelper;
    protected DictionaryHelperForUpdate<T, U> updateDictHelper;
    protected DictionaryHelperForActivity<T> activityDictHelper;

    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.listHelper = factory.constructHelperForList(this);
        this.createDictHelper = factory.constructHelperForCreate(this);
        this.updateDictHelper = factory.constructHelperForUpdate(this);
        this.activityDictHelper = factory.constructHelperForActivity(this);

        createDictHelper.init();
    }

    @Override
    abstract public AbstractAliasedDictRepository<T, A> getRepository();

    @Override
    public Object buildResponseDTO(T entity) {
        return new AliasedDictResponseDTO(entity);
    }

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

    @Override
    public List<T> create(List<D> request) {
        return createDictHelper.create(request);
    }

    @Override
    public T create(D request) {
        return createDictHelper.create(request);
    }

    @Override
    public T update(Long id, U request) {
        return updateDictHelper.update(id, request);
    }

    @Override
    public void open(Long id) {
        activityDictHelper.open(id);
    }

    @Override
    public void close(Long id) {
        activityDictHelper.close(id);
    }
}
