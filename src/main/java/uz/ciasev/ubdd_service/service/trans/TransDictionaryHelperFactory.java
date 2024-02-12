package uz.ciasev.ubdd_service.service.trans;

import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

public interface TransDictionaryHelperFactory {

    <T extends AbstractTransEntity, D extends TransEntityCreateRequest<T>> TransDictionaryHelperForCreate<T, D> constructHelperForCreate(TransEntityServiceWithRepository<T> owner);

    <T extends AbstractTransEntity> TransDictionaryHelperForDelete<T> constructHelperForDelete(TransEntityServiceWithRepository<T> owner);

}
