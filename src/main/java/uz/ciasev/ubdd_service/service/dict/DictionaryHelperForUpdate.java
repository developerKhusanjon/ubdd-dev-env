package uz.ciasev.ubdd_service.service.dict;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;
import uz.ciasev.ubdd_service.service.dict.validation.DictionaryValidationService;
import uz.ciasev.ubdd_service.service.history.DictionaryAdminHistoryService;

@RequiredArgsConstructor
public class DictionaryHelperForUpdate<T extends AbstractDict, D extends DictUpdateRequest<T>> {

    private final DictionaryValidationService validationService;
    private final DictionaryAdminHistoryService historyService;
    private final DictionaryServiceWithRepository<T> owner;

    @Transactional
    public T update(Long id, D request) {
        T entity = owner.getById(id);

        validationService.validateUpdate(owner, entity, request);

        request.applyToOld(entity);

        owner.getRepository().save(entity);
        historyService.register(entity, DictAdminHistoricAction.UPDATE);

        return entity;
    }
}
