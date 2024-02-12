package uz.ciasev.ubdd_service.service.dict.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.CourtConsideringBasisCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.CourtConsideringBasisUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtConsideringBasisResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.repository.dict.court.CourtConsideringBasisRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class CourtConsideringBasisDictionaryServiceImpl extends AbstractDictionaryCRUDService<CourtConsideringBasis, CourtConsideringBasisCreateRequestDTO, CourtConsideringBasisUpdateRequestDTO>
        implements CourtConsideringBasisDictionaryService {

    private final String subPath = "court-considering-basis";
    private final TypeReference<CourtConsideringBasisCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<CourtConsideringBasisUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final CourtConsideringBasisRepository repository;
    private final Class<CourtConsideringBasis> entityClass = CourtConsideringBasis.class;

    @Override
    public CourtConsideringBasisResponseDTO buildResponseDTO(CourtConsideringBasis entity) {
        return new CourtConsideringBasisResponseDTO(entity);
    }
}
