package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.GcpTransDivisionResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransDivisionCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransDivision;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransDivisionRepository;
import uz.ciasev.ubdd_service.service.address.AddressValidationService;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransDivisionServiceImpl extends AbstractTransEntityCRDService<GcpTransDivision, GcpTransDivisionCreateRequestDTO>
        implements GcpTransDivisionService {

    private final AddressValidationService addressValidationService;

    private final GcpTransDivisionRepository repository;
    private final String subPath = "gcp/division";
    private final Class<GcpTransDivision> entityClass = GcpTransDivision.class;

    private final TypeReference<GcpTransDivisionCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransDivision create(GcpTransDivisionCreateRequestDTO requestDTO) {
        validate(requestDTO);
        addressValidationService.validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransDivision entity) {
        return new GcpTransDivisionResponseDTO(entity);
    }

    private void validate(GcpTransDivisionCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
