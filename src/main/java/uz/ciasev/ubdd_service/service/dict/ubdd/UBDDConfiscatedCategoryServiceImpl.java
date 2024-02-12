package uz.ciasev.ubdd_service.service.dict.ubdd;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDConfiscatedCategoryCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDConfiscatedCategoryUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd.UBDDConfiscatedCategoryResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDConfiscatedCategory;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDConfiscatedCategoryRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDConfiscatedCategoryServiceImpl extends AbstractDictionaryCRUDService<UBDDConfiscatedCategory, UBDDConfiscatedCategoryCreateRequestDTO, UBDDConfiscatedCategoryUpdateRequestDTO>
        implements UBDDConfiscatedCategoryService {

    private final String subPath = "ubdd-confiscated-category";
    private final TypeReference<UBDDConfiscatedCategoryCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<UBDDConfiscatedCategoryUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final UBDDConfiscatedCategoryRepository repository;
    private final Class<UBDDConfiscatedCategory> entityClass = UBDDConfiscatedCategory.class;

    @Override
    public UBDDConfiscatedCategoryResponseDTO buildResponseDTO(UBDDConfiscatedCategory entity) {
        return new UBDDConfiscatedCategoryResponseDTO(entity);
    }
}
