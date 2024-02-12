package uz.ciasev.ubdd_service.service.trans;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractTransEntityCRDService<T extends AbstractTransEntity, D extends TransEntityCreateRequest<T>>
        implements TransEntityServiceWithRepository<T>, TransEntityCRDService<T, D> {

    protected TransDictionaryHelperForCreate<T, D> createDictHelper;
    protected TransDictionaryHelperForDelete<T> deleteDictHelper;

    @Setter
    @Autowired
    protected TransDictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.createDictHelper = factory.constructHelperForCreate(this);
        this.deleteDictHelper = factory.constructHelperForDelete(this);

        createDictHelper.init();
    }

    @Override
    @Transactional
    public List<T> create(List<D> request) {
        return request.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    public T create(D request) {
        return createDictHelper.create(request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        deleteDictHelper.delete(getById(id));
    }

    @Override
    public Page<T> findAll(Map<String, String> filters, Pageable pageable) {
        return getRepository().findAll(pageable);
    }
}
