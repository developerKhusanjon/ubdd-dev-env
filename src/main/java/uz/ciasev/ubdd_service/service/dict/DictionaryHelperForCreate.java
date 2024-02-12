package uz.ciasev.ubdd_service.service.dict;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.dict.validation.DictionaryValidationService;
import uz.ciasev.ubdd_service.service.history.DictionaryAdminHistoryService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DictionaryHelperForCreate<T extends AbstractDict, D extends DictCreateRequest<T>> {

    private final DictionaryValidationService validationService;
    private final DictionaryAdminHistoryService historyService;
    private final DictionaryServiceWithRepository<T> owner;
    private Constructor<T> defaultConstructor;

    @PostConstruct
    public void init() {
        try {
            this.defaultConstructor = owner.getEntityClass().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new ImplementationException(String.format(
                    "In entity class %s not found public default constructor. It need for creating dictionary by admin",
                    owner.getEntityClass().getName()
            ));
        }


        // check default constructor is usable
        T newEntity = createNewInstance();
    }

    @Transactional
    public List<T> create(List<D> requestList) {
        return requestList.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public T create(D request) {
        validationService.validateCreate(owner, request);

        T newEntity = createNewInstance();
        request.applyToNew(newEntity);

        owner.getRepository().saveAndFlush(newEntity);
        historyService.register(newEntity, DictAdminHistoricAction.CREATE);

        return newEntity;
    }

    private T createNewInstance() {
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException e) {
            throw new ImplementationException(String.format(
                    "Can not create new instance of class %s by constructor %s. It need for creating dictionary by admin",
                    owner.getEntityClass().getName(),
                    defaultConstructor.getName()
            ));
        } catch (IllegalAccessException e) {
            throw new ImplementationException(String.format(
                    "Constructor %s mast be public. It need for creating dictionary by admin", 
                    defaultConstructor.getName()
            ));
        } catch (InvocationTargetException e) {
            throw new ImplementationException(String.format(
                    "Attempt create new instance of class %s by constructor %s throws exception %s. It need for creating dictionary by admin",
                    defaultConstructor.getName(),
                    owner.getEntityClass().getName(),
                    e.getTargetException().toString()
            ));
        }
    }
}
