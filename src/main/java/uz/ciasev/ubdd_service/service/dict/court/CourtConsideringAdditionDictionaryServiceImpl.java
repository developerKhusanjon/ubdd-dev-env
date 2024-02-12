package uz.ciasev.ubdd_service.service.dict.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.CourtConsideringAdditionCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.CourtConsideringAdditionUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtConsideringAdditionResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.repository.dict.court.CourtConsideringAdditionRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class CourtConsideringAdditionDictionaryServiceImpl extends AbstractDictionaryCRUDService<CourtConsideringAddition, CourtConsideringAdditionCreateRequestDTO, CourtConsideringAdditionUpdateRequestDTO>
        implements CourtConsideringAdditionDictionaryService {

    private final String subPath = "court-considering-addition";
    private final TypeReference<CourtConsideringAdditionCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<CourtConsideringAdditionUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final CourtConsideringAdditionRepository repository;
    private final Class<CourtConsideringAddition> entityClass = CourtConsideringAddition.class;

    @Override
    public CourtConsideringAdditionResponseDTO buildResponseDTO(CourtConsideringAddition entity) {
        return new CourtConsideringAdditionResponseDTO(entity);
    }

    @Override
    public CourtConsideringAdditionResponseDTO buildListResponseDTO(CourtConsideringAddition entity) {return new CourtConsideringAdditionResponseDTO(entity);}

}
