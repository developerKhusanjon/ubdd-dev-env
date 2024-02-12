package uz.ciasev.ubdd_service.service.dict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import java.util.Map;

@Service
public abstract class AbstractDictionaryListService<T extends AbstractDict> implements DictionaryServiceWithRepository<T> {

    protected final DictionaryHelperForList<T> listHelper;

    protected AbstractDictionaryListService() {
        this.listHelper = new DictionaryHelperForList<>(this);
    }

    @Override
    public Page<T> findAll(Map<String, String> filters, Pageable pageable) {
        return listHelper.findAll(filters, pageable);
    }
}
