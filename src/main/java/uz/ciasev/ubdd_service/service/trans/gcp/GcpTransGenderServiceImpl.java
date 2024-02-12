package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.SimpleGcpTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransGenderCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransGender;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransGenderRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransGenderServiceImpl extends AbstractTransEntityCRDService<GcpTransGender, GcpTransGenderCreateRequestDTO>
        implements GcpTransGenderService {

    private final GcpTransGenderRepository repository;
    private final String subPath = "gcp/gender";
    private final Class<GcpTransGender> entityClass = GcpTransGender.class;

    private final TypeReference<GcpTransGenderCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransGender create(GcpTransGenderCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransGender entity) {
        return new SimpleGcpTransEntityResponseDTO(entity);
    }

    private void validate(GcpTransGenderCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
