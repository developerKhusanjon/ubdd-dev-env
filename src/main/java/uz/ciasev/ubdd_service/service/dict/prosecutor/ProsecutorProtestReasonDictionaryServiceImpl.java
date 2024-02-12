package uz.ciasev.ubdd_service.service.dict.prosecutor;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ProsecutorProtestReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ProsecutorProtestReasonResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;
import uz.ciasev.ubdd_service.repository.dict.prosecutor.ProsecutorProtestReasonRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class ProsecutorProtestReasonDictionaryServiceImpl extends AbstractDictionaryCRUDService<ProsecutorProtestReason, ProsecutorProtestReasonRequestDTO, ProsecutorProtestReasonRequestDTO>
        implements ProsecutorProtestReasonDictionaryService {

    private final String subPath = "prosecutor-protest-reasons";
    private final TypeReference<ProsecutorProtestReasonRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ProsecutorProtestReasonRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final ProsecutorProtestReasonRepository repository;
    private final Class<ProsecutorProtestReason> entityClass = ProsecutorProtestReason.class;

    @Override
    public ProsecutorProtestReasonResponseDTO buildResponseDTO(ProsecutorProtestReason entity) {
        return new ProsecutorProtestReasonResponseDTO(entity);
    }

}
