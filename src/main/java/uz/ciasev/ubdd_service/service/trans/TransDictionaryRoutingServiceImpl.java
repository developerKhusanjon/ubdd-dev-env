package uz.ciasev.ubdd_service.service.trans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.exception.dict.DuplicatePathInDictServicesException;
import uz.ciasev.ubdd_service.exception.dict.OperationNotAllowedForEntity;
import uz.ciasev.ubdd_service.exception.dict.ResourceByUriNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransDictionaryRoutingServiceImpl implements TransDictionaryRoutingService {
    private final Map<String, TransEntityService<?>> serviceMap;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public TransDictionaryRoutingServiceImpl(
            List<TransEntityService<?>> dictInheritorsList,
            ObjectMapper objectMapper,
            ValidatorFactory factory
    ) {
        this.serviceMap = constructServiceMapFromDictServiceInheritors(dictInheritorsList);
        this.objectMapper = objectMapper;
        this.validator = factory.getValidator();
    }

    @Override
    public <T> Page<Object> findAll(String path, Map<String, String> filters, Pageable pageable) {
        TransEntityService<T> dictionaryService = getServiceByPath(path);

        return dictionaryService.findAll(filters, pageable).map(dictionaryService::buildListResponseDTO);
    }

    @Override
    public <T, D> List<Object> create(String path, List<JsonNode> body) {
        CreateTransEntityService<T, D> createDictionaryService = getSpecializedServiceByPath(CreateTransEntityService.class, path);
        TypeReference<? extends D> requestDTOClass = createDictionaryService.getCreateRequestDTOClass();
        List<D> requestDTOList;

        requestDTOList = body.stream()
                .map(object -> convertToRequestDTO(object, requestDTOClass))
                .collect(Collectors.toList());
        requestDTOList.forEach(this::validate);

        return createDictionaryService.create(requestDTOList).stream()
                .map(createDictionaryService::buildListResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public <T> Object getById(String path, Long id) {
        TransEntityService<T> dictionaryService = getServiceByPath(path);

        return dictionaryService.buildResponseDTO(dictionaryService.getById(id));
    }

//    @Override
//    public <T, U> Object updateById(String path, Long id, JsonNode body) {
//        UpdateTransEntityService<T, U> updateDictionaryService = getSpecializedServiceByPath(UpdateTransEntityService.class, path);
//        TypeReference<? extends U> requestDTOClass = updateDictionaryService.getUpdateRequestDTOClass();
//
//        U requestDTO = convertToRequestDTO(body, requestDTOClass);
//        validate(requestDTO);
//
//        return updateDictionaryService.buildResponseDTO(updateDictionaryService.update(id, requestDTO));
//    }

    @Override
    public <T> void delete(String path, Long id) {
        DeleteTransEntityService<T> deleteDictionaryService = getSpecializedServiceByPath(DeleteTransEntityService.class, path);
        deleteDictionaryService.delete(id);
    }

    private Map<String, TransEntityService<?>> constructServiceMapFromDictServiceInheritors (List<TransEntityService<?>> dictInheritorsList) {
        Map<String, TransEntityService<?>> serviceMap = new HashMap<>();

        for (TransEntityService<?> service : dictInheritorsList) {
            String path = service.getSubPath();

            if (path == null) continue;

            if (serviceMap.containsKey(path)) throw new DuplicatePathInDictServicesException(
                    service.getClass().getSimpleName(),
                    serviceMap.get(path).getClass().getSimpleName(),
                    "TransEntityService");

            serviceMap.put(path, service);
            }
        return serviceMap;
    }

    private <T> TransEntityService<T> getServiceByPath(String path) {
        return (TransEntityService<T>) Optional.ofNullable(this.serviceMap.get(path))
                .orElseThrow(() -> new ResourceByUriNotFoundException(String.format("settings/trans/%s", path)));
    }

    private <T, S extends TransEntityService<T>> S getSpecializedServiceByPath(Class<S> serviceClass, String path) {
        TransEntityService<?> service = getServiceByPath(path);
        try {
            return serviceClass.cast(service);
        } catch (ClassCastException e) {
            throw new OperationNotAllowedForEntity();
        }
    }

    private <T> void validate(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private <D> D convertToRequestDTO(JsonNode object, TypeReference<? extends D> requestDTOClass) {
        try {
            return objectMapper.convertValue(object, requestDTOClass);
        } catch (IllegalArgumentException e) {
            throw new HttpMessageNotReadableException(
                    "Request data cannot be mapped to object in TransDictionaryRoutingServiceImpl",
                    ExceptionUtils.getRootCause(e));
        }
    }
}
