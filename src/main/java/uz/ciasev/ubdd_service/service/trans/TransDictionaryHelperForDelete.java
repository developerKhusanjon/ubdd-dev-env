package uz.ciasev.ubdd_service.service.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.history.TransDictAdminHistoricAction;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.service.history.TransDictionaryAdminHistoryService;

@RequiredArgsConstructor
public class TransDictionaryHelperForDelete<T extends AbstractTransEntity> {
    private final TransDictionaryAdminHistoryService historyService;
    private final TransEntityServiceWithRepository<T> owner;

    @Transactional
    public void delete(T entity) {
        owner.getRepository().delete(entity);
        historyService.register(entity, TransDictAdminHistoricAction.DELETE);
    }
}
