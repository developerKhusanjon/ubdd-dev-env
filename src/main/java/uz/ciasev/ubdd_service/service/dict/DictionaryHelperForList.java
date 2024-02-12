package uz.ciasev.ubdd_service.service.dict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.repository.dict.DictionaryFilterBean;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import java.util.Map;

public class DictionaryHelperForList<T extends AbstractDict> {

    private final DictionaryServiceWithRepository<T> owner;
    private final FilterHelper<T> filterHelper;

    public DictionaryHelperForList(DictionaryServiceWithRepository<T> owner, FilterHelper<T> filterHelper) {
        this.owner = owner;
        this.filterHelper = filterHelper;
    }

    public DictionaryHelperForList(DictionaryServiceWithRepository<T> owner) {
        this(owner, DictionaryFilterBean.getBase());
    }

    public Page<T> findAll(Map<String, String> filters, Pageable pageable) {
        return owner.getRepository().findAll(
                filterHelper.getParamsSpecification(filters),
                pageable
        );
    }
}
