package uz.ciasev.ubdd_service.service.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.history.TransDictAdminHistoricAction;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.history.TransDictionaryAdminHistoryService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
public class TransDictionaryHelperForCreate<T extends AbstractTransEntity, D extends TransEntityCreateRequest<T>> {
    private final TransDictionaryAdminHistoryService historyService;
    private final TransEntityServiceWithRepository<T> owner;
    private Constructor<T> defaultConstructor;

    @PostConstruct
    public void init() {
        try {
            this.defaultConstructor = owner.getEntityClass().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new ImplementationException(String.format(
                    "In entity class %s not found public default constructor. It is required for creating dictionary by admin",
                    owner.getEntityClass().getName()
            ));
        }

        // check default constructor is usable
        T newEntity = createNewInstance();
    }

    @Transactional
    public T create(D request) {
        T newEntity = createNewInstance();
        request.applyToNew(newEntity);

        owner.getRepository().saveAndFlush(newEntity);
        historyService.register(newEntity, TransDictAdminHistoricAction.CREATE);

        return newEntity;
    }

    private T createNewInstance() {
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException e) {
            throw new ImplementationException(String.format(
                    "Can not create new instance of class %s by constructor %s. It is required for creating dictionary by admin",
                    owner.getEntityClass().getName(),
                    defaultConstructor.getName()
            ));
        } catch (IllegalAccessException e) {
            throw new ImplementationException(String.format(
                    "Constructor %s must be public. It is required for creating dictionary by admin",
                    defaultConstructor.getName()
            ));
        } catch (InvocationTargetException e) {
            throw new ImplementationException(String.format(
                    "Attempt to create new instance of class %s by constructor %s throws exception %s. Constructor required for creating dictionary by admin",
                    defaultConstructor.getName(),
                    owner.getEntityClass().getName(),
                    e.getTargetException().toString()
            ));
        }
    }
}
