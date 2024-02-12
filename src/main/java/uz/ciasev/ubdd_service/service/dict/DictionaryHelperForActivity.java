package uz.ciasev.ubdd_service.service.dict;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;
import uz.ciasev.ubdd_service.service.history.DictionaryAdminHistoryService;

@RequiredArgsConstructor
public class DictionaryHelperForActivity<T extends AbstractDict> {

    private final DictionaryAdminHistoryService historyService;
    private final DictionaryServiceWithRepository<T> owner;

    @Transactional
    public T open(Long id) {
        T entity = owner.getById(id);
        entity.open();

        owner.getRepository().save(entity);
        historyService.register(entity, DictAdminHistoricAction.OPEN);

        return entity;
    }

    @Transactional
    public T close(Long id) {
        T entity = owner.getById(id);
        entity.close();

        owner.getRepository().save(entity);
        historyService.register(entity, DictAdminHistoricAction.CLOSE);

        return entity;
    }
}
