package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.SimpleGcpTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransRegionCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransRegion;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransRegionRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransRegionServiceImpl extends AbstractTransEntityCRDService<GcpTransRegion, GcpTransRegionCreateRequestDTO>
        implements GcpTransRegionService {

    private final GcpTransRegionRepository repository;
    private final String subPath = "gcp/region";
    private final Class<GcpTransRegion> entityClass = GcpTransRegion.class;

    private final TypeReference<GcpTransRegionCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransRegion create(GcpTransRegionCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransRegion entity) {
        return new SimpleGcpTransEntityResponseDTO(entity);
    }

    private void validate(GcpTransRegionCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
