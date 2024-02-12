package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.SimpleGcpTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransDistrictCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransDistrict;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransDistrictRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransDistrictServiceImpl extends AbstractTransEntityCRDService<GcpTransDistrict, GcpTransDistrictCreateRequestDTO>
        implements GcpTransDistrictService {

    private final GcpTransDistrictRepository repository;
    private final String subPath = "gcp/district";
    private final Class<GcpTransDistrict> entityClass = GcpTransDistrict.class;

    private final TypeReference<GcpTransDistrictCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransDistrict create(GcpTransDistrictCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransDistrict entity) {
        return new SimpleGcpTransEntityResponseDTO(entity);
    }

    private void validate(GcpTransDistrictCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
