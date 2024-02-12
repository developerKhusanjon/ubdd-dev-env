package uz.ciasev.ubdd_service.service.trans.autocon;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.request.autocon.AutoconTransRegionCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.response.SimpleTransEntityResponseDTO;
import uz.ciasev.ubdd_service.entity.trans.autocon.AutoconTransRegion;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.AutoconTransRegionRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class AutoconTransRegionAdminServiceImpl extends AbstractTransEntityCRDService<AutoconTransRegion, AutoconTransRegionCreateRequestDTO>
        implements AutoconTransRegionAdminService {

    private final AutoconTransRegionRepository repository;
    private final String subPath = "autocon/region";
    private final Class<AutoconTransRegion> entityClass = AutoconTransRegion.class;

    private final TypeReference<AutoconTransRegionCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public AutoconTransRegion create(AutoconTransRegionCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(AutoconTransRegion entity) {
        return new SimpleTransEntityResponseDTO(entity);
    }

    private void validate(AutoconTransRegionCreateRequestDTO requestDTO) {
        Long internalId = requestDTO.getInternal().getId();

        if (repository.existsByInternalId(internalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "internalId", internalId);
        }
    }
}
