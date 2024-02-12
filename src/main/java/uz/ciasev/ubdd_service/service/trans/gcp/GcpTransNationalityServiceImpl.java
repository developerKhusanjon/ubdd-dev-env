package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.SimpleGcpTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransNationalityCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransNationality;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransNationalityRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransNationalityServiceImpl extends AbstractTransEntityCRDService<GcpTransNationality, GcpTransNationalityCreateRequestDTO>
        implements GcpTransNationalityService {

    private final GcpTransNationalityRepository repository;
    private final String subPath = "gcp/nationality";
    private final Class<GcpTransNationality> entityClass = GcpTransNationality.class;

    private final TypeReference<GcpTransNationalityCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransNationality create(GcpTransNationalityCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransNationality entity) {
        return new SimpleGcpTransEntityResponseDTO(entity);
    }

    private void validate(GcpTransNationalityCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
