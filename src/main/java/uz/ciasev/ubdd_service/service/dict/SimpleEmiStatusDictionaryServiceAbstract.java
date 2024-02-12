package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiStatusDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.StatusDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleEmiStatusDictionaryServiceAbstract<T extends AbstractEmiStatusDict>
        extends AbstractDictionaryCRUDService<T, EmiStatusDictCreateRequestDTO<T>, EmiStatusDictUpdateRequestDTO<T>>
        implements SimpleEmiStatusDictionaryService<T> {

    private final TypeReference<EmiStatusDictCreateRequestDTO<T>> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<EmiStatusDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};

    @Override
    public StatusDictResponseDTO buildResponseDTO(T entity) {
        return new StatusDictResponseDTO(entity);
    }

    @Override
    public StatusDictResponseDTO buildListResponseDTO(T entity) {
        return buildResponseDTO(entity);
    }
}
