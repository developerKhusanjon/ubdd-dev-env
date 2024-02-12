package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalStatusDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ExternalStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.StatusDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleExternalIdStatusDictionaryServiceAbstract<T extends AbstractExternalStatusDictEntity>
        extends AbstractDictionaryCRUDService<T, ExternalStatusDictCreateRequestDTO<T>, ExternalStatusDictUpdateRequestDTO<T>>
        implements SimpleExternalIdStatusDictionaryService<T> {

    private final TypeReference<ExternalStatusDictCreateRequestDTO<T>> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ExternalStatusDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};

    @Override
    public StatusDictResponseDTO buildResponseDTO(T entity) {
        return new StatusDictResponseDTO(entity);
    }

    @Override
    public StatusDictResponseDTO buildListResponseDTO(T entity) {
        return buildResponseDTO(entity);
    }
}
