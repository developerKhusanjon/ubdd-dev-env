package uz.ciasev.ubdd_service.service.dict;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


public abstract class AbstractDictionaryCRUDService<T extends AbstractDict, D extends DictCreateRequest<T>, U extends DictUpdateRequest<T>>
        implements DictionaryServiceWithRepository<T>, DictionaryCRUDService<T, D, U> {

    protected DictionaryHelperForList<T> listHelper;
    protected DictionaryHelperForCreate<T, D> createDictHelper;
    protected DictionaryHelperForUpdate<T, U> updateDictHelper;
    protected DictionaryHelperForActivity<T> activityDictHelper;

    @Setter @Autowired
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
    public Object buildResponseDTO(T entity) {
        return new DictResponseDTO(entity);
    }

    @Override
    public Page<T> findAll(Map<String, String> filters, Pageable pageable) {
        return listHelper.findAll(filters, pageable);
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
