package uz.ciasev.ubdd_service.service.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.service.history.TransDictionaryAdminHistoryService;


@Service
@RequiredArgsConstructor
public class TransDictionaryHelperFactoryImpl implements TransDictionaryHelperFactory {

    private final ApplicationContext context;
    private final TransDictionaryAdminHistoryService historyService;

    @Override
    public <T extends AbstractTransEntity, D extends TransEntityCreateRequest<T>> TransDictionaryHelperForCreate<T, D> constructHelperForCreate(TransEntityServiceWithRepository<T> owner) {
        TransDictionaryHelperForCreate<T, D> instance = new TransDictionaryHelperForCreate<>(historyService, owner);
        return (TransDictionaryHelperForCreate<T, D>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "TransDictionaryHelperForCreate-for-" + owner.hashCode());
    }

    @Override
    public <T extends AbstractTransEntity> TransDictionaryHelperForDelete<T> constructHelperForDelete(TransEntityServiceWithRepository<T> owner) {
        TransDictionaryHelperForDelete<T> instance = new TransDictionaryHelperForDelete<>(historyService, owner);
        return (TransDictionaryHelperForDelete<T>) context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(instance, "TransDictionaryHelperForDelete-for-" + owner.hashCode());
    }
}
