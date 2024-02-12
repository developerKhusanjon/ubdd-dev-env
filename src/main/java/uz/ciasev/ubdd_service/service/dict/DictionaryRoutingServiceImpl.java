package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.dict.AliasDescriptionResponseDTO;
import uz.ciasev.ubdd_service.entity.AliasDescription;
import uz.ciasev.ubdd_service.exception.dict.DuplicatePathInDictServicesException;
import uz.ciasev.ubdd_service.exception.dict.OperationNotAllowedForEntity;
import uz.ciasev.ubdd_service.exception.dict.ResourceByUriNotFoundException;
import uz.ciasev.ubdd_service.service.AliasDescriptionService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictionaryRoutingServiceImpl implements DictionaryRoutingService {
    private final Map<String, DictionaryService<?>> serviceMap;
    private final ObjectMapper objectMapper;
    private final AliasDescriptionService aliasDescriptionService;
    private final Validator validator;

    @Autowired
    public DictionaryRoutingServiceImpl(
            List<DictionaryService<?>> dictInheritorsList,
            ObjectMapper objectMapper,
            AliasDescriptionService aliasDescriptionService,
            ValidatorFactory factory
    ) {
        this.serviceMap = constructServiceMapFromDictServiceInheritors(dictInheritorsList);
        this.aliasDescriptionService = aliasDescriptionService;
        this.objectMapper = objectMapper;
        this.validator = factory.getValidator();
    }

    @Override
    public <T> Page<Object> findAll(String path, Map<String, String> filters, Pageable pageable) {
        DictionaryService<T> dictionaryService = getServiceByPath(path);

        return dictionaryService.findAll(filters, pageable).map(dictionaryService::buildListResponseDTO);
    }

    @Override
    public <T, D> List<Object> create(String path, List<JsonNode> body) {
        CreateDictionaryService<T, D> createDictionaryService = getSpecializedServiceByPath(CreateDictionaryService.class, path);
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
        DictionaryService<T> dictionaryService = getServiceByPath(path);

        return dictionaryService.buildResponseDTO(dictionaryService.getById(id));
    }

    @Override
    public <T, U> Object updateById(String path, Long id, JsonNode body) {
        UpdateDictionaryService<T, U> updateDictionaryService = getSpecializedServiceByPath(UpdateDictionaryService.class, path);
        TypeReference<? extends U> requestDTOClass = updateDictionaryService.getUpdateRequestDTOClass();

        U requestDTO = convertToRequestDTO(body, requestDTOClass);
        validate(requestDTO);

        return updateDictionaryService.buildResponseDTO(updateDictionaryService.update(id, requestDTO));
    }

    @Override
    public <T> void open(String path, Long id) {
        ActivityDictionaryService<T> activityDictionaryService = getSpecializedServiceByPath(ActivityDictionaryService.class, path);
        activityDictionaryService.open(id);
    }

    @Override
    public <T> void close(String path, Long id) {
        ActivityDictionaryService<T> activityDictionaryService = getSpecializedServiceByPath(ActivityDictionaryService.class, path);
        activityDictionaryService.close(id);
    }

    @Override
    public <T, A extends Enum<A>> List<AliasDescriptionResponseDTO> getAliasList(String path) {
        AliasedDictionaryService<T, A> aliasedDictionaryService = getSpecializedServiceByPath(AliasedDictionaryService.class, path);
        List<AliasDescription> aliasDescriptionList = aliasDescriptionService.findAllByAliasEnum(aliasedDictionaryService.getAliasClass());

        return aliasDescriptionList.stream()
                .map(AliasDescriptionResponseDTO::new)
                .collect(Collectors.toList());
    }

    private Map<String, DictionaryService<?>> constructServiceMapFromDictServiceInheritors (List<DictionaryService<?>> dictInheritorsList) {
        Map<String, DictionaryService<?>> serviceMap = new HashMap<>();

        for (DictionaryService<?> service : dictInheritorsList) {
            String path = service.getSubPath();

            if (path == null) continue;

            if (serviceMap.containsKey(path)) throw new DuplicatePathInDictServicesException(
                    service.getClass().getSimpleName(),
                    serviceMap.get(path).getClass().getSimpleName(),
                    "DictionaryService");

            serviceMap.put(path, service);
            }
        return serviceMap;
    }

    private <T> DictionaryService<T> getServiceByPath(String path) {
        return (DictionaryService<T>) Optional.ofNullable(this.serviceMap.get(path)).orElseThrow(() -> new ResourceByUriNotFoundException(String.format("catalogs/%s", path)));
    }

    private <T, S extends DictionaryService<T>> S getSpecializedServiceByPath(Class<S> serviceClass, String path) {
        DictionaryService<?> service = getServiceByPath(path);
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
                    "Request data cannot be mapped to object in DictionaryRoutingServiceImpl",
                    ExceptionUtils.getRootCause(e));
        }
    }
}
