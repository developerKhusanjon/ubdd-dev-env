package uz.ciasev.ubdd_service.service.dict;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.repository.dict.DictionaryFilterBean;
import uz.ciasev.ubdd_service.service.dict.validation.DictionaryValidationService;
import uz.ciasev.ubdd_service.service.history.DictionaryAdminHistoryService;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class DictionaryHelperFactoryImpl implements DictionaryHelperFactory {

    private final ApplicationContext context;
    private final DictionaryAdminHistoryService historyService;
    private final DictionaryValidationService validationService;

    private final Map<Class<?>, FilterHelper<?>> specificFilterHelper = Map.of(
            District.class, DictionaryFilterBean.getForDistrict()
    );

    @Override
    public <T extends AbstractDict, D extends DictCreateRequest<T>> DictionaryHelperForCreate<T, D> constructHelperForCreate(DictionaryServiceWithRepository<T> owner) {
        DictionaryHelperForCreate<T, D> instance = new DictionaryHelperForCreate<>(validationService, historyService, owner);
        return (DictionaryHelperForCreate<T, D>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "DictionaryHelperForCreate-for-" + owner.hashCode());
    }

    @Override
    public <T extends AbstractDict> DictionaryHelperForList<T> constructHelperForList(DictionaryServiceWithRepository<T> owner) {
        DictionaryHelperForList<T> instance = createHelperForList(owner);

        return (DictionaryHelperForList<T>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "DictionaryHelperForList-for-" + owner.hashCode());
    }

    @Override
    public <T extends AbstractDict, U extends DictUpdateRequest<T>> DictionaryHelperForUpdate<T, U> constructHelperForUpdate(DictionaryServiceWithRepository<T> owner) {
        DictionaryHelperForUpdate<T, U> instance = new DictionaryHelperForUpdate<>(validationService, historyService, owner);
        return (DictionaryHelperForUpdate<T, U>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "DictionaryHelperForUpdate-for-" + owner.hashCode());
    }

    @Override
    public <T extends AbstractDict> DictionaryHelperForActivity<T> constructHelperForActivity(DictionaryServiceWithRepository<T> owner) {
        DictionaryHelperForActivity<T> instance = new DictionaryHelperForActivity<>(historyService, owner);
        return (DictionaryHelperForActivity<T>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "DictionaryHelperForActivity-for-" + owner.hashCode());
    }

    private <T extends AbstractDict> DictionaryHelperForList<T> createHelperForList(DictionaryServiceWithRepository<T> owner) {
        FilterHelper<T> filter = (FilterHelper<T>) specificFilterHelper.get(owner.getEntityClass());
        if (filter != null) {
            return new DictionaryHelperForList<>(owner, filter);
        }

        return new DictionaryHelperForList<>(owner);
    }
}
