package uz.ciasev.ubdd_service.service.dict.ubdd;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.TintingCategoryRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd.TintingCategoryResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;
import uz.ciasev.ubdd_service.repository.dict.ubdd.TintingCategoryRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class TintingCategoryServiceImpl extends AbstractDictionaryCRUDService<TintingCategory, TintingCategoryRequestDTO, TintingCategoryRequestDTO>
        implements TintingCategoryService {

    private final String subPath = "ubdd-tinting-categories";
    private final TypeReference<TintingCategoryRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<TintingCategoryRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final TintingCategoryRepository repository;
    private final Class<TintingCategory> entityClass = TintingCategory.class;

    @Override
    public TintingCategoryResponseDTO buildResponseDTO(TintingCategory entity) {
        return new TintingCategoryResponseDTO(entity);
    }

    @Override
    public TintingCategoryResponseDTO buildListResponseDTO(TintingCategory entity) {return new TintingCategoryResponseDTO(entity);}
}
