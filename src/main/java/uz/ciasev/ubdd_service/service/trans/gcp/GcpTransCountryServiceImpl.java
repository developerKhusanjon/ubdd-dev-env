package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.SimpleGcpTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransCountryCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransCountry;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransCountryRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransCountryServiceImpl extends AbstractTransEntityCRDService<GcpTransCountry, GcpTransCountryCreateRequestDTO>
        implements GcpTransCountryService {

    private final GcpTransCountryRepository repository;
    private final String subPath = "gcp/country";
    private final Class<GcpTransCountry> entityClass = GcpTransCountry.class;

    private final TypeReference<GcpTransCountryCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransCountry create(GcpTransCountryCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransCountry entity) {
        return new SimpleGcpTransEntityResponseDTO(entity);
    }

    private void validate(GcpTransCountryCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
