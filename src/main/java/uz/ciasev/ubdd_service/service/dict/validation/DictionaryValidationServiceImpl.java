package uz.ciasev.ubdd_service.service.dict.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.service.dict.validation.validators.DictionaryCreateValidator;
import uz.ciasev.ubdd_service.service.dict.validation.validators.DictionaryUpdateValidator;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DictionaryValidationServiceImpl implements DictionaryValidationService {

    private final  List<DictionaryCreateValidator> createValidators;
    private final List<DictionaryUpdateValidator> updateValidators;

    @Override
    public <T extends AbstractDict, D extends DictCreateRequest<T>> void validateCreate(DictionaryServiceWithRepository<T> service, D request) {
        createValidators.stream()
                .filter(v -> v.getValidatedType().isAssignableFrom(service.getEntityClass()))
                .forEach(v -> {
                    if (!v.getRequestType().isInstance(request)) {
                        throw new ImplementationException(String.format("Dictionary validator %s do not support request type %s", v.getClass().getName(), request.getClass().getName()));
                    }
                    v.validate(service, request);
                });
    }

    @Override
    public <T extends AbstractDict, U extends DictUpdateRequest<T>> void validateUpdate(DictionaryServiceWithRepository<T> service, T entity, U request) {
        updateValidators.stream()
                .filter(v -> v.getValidatedType().isInstance(entity))
                .forEach(v -> {
                    if (!v.getRequestType().isInstance(request)) {
                        throw new ImplementationException(String.format("Dictionary validator %s do not support request type %s", v.getClass().getName(), request.getClass().getName()));
                    }
                    v.validate(service, entity, request);
                });
    }
}
