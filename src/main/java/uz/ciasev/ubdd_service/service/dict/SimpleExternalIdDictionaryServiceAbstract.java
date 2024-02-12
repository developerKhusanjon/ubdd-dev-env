package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleExternalIdDictionaryServiceAbstract<T extends AbstractExternalDictEntity>
        extends AbstractDictionaryCRUDService<T, ExternalDictCreateRequestDTO<T>, ExternalDictUpdateRequestDTO<T>>
        implements SimpleExternalIdDictionaryService<T> {

    private final TypeReference<ExternalDictCreateRequestDTO<T>> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ExternalDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};
}
