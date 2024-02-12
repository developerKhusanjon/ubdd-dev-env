package uz.ciasev.ubdd_service.service.dict.evidence;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EvidenceCategoryCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EvidenceCategoryUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.EvidenceCategoryResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.repository.dict.evidence.EvidenceCategoryRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class EvidenceCategoryDictionaryServiceImpl extends AbstractDictionaryCRUDService<EvidenceCategory, EvidenceCategoryCreateRequestDTO, EvidenceCategoryUpdateRequestDTO>
        implements EvidenceCategoryDictionaryService {

    private final String subPath = "evidence-categories";
    private final TypeReference<EvidenceCategoryCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<EvidenceCategoryUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final EvidenceCategoryRepository repository;
    private final Class<EvidenceCategory> entityClass = EvidenceCategory.class;

    @Override
    public EvidenceCategoryResponseDTO buildResponseDTO(EvidenceCategory entity) {
        return new EvidenceCategoryResponseDTO(entity);
    }
}
