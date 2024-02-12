package uz.ciasev.ubdd_service.service.trans.gcp;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.gcp.GcpTransPersonDocumentTypeResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransPersonDocumentTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransPersonDocumentType;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.gcp.GcpTransPersonDocumentTypeRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class GcpTransPersonDocumentTypeServiceImpl extends AbstractTransEntityCRDService<GcpTransPersonDocumentType, GcpTransPersonDocumentTypeCreateRequestDTO>
        implements GcpTransPersonDocumentTypeService {

    private final GcpTransPersonDocumentTypeRepository repository;
    private final String subPath = "gcp/person-document-type";
    private final Class<GcpTransPersonDocumentType> entityClass = GcpTransPersonDocumentType.class;

    private final TypeReference<GcpTransPersonDocumentTypeCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public GcpTransPersonDocumentType create(GcpTransPersonDocumentTypeCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(GcpTransPersonDocumentType entity) {
        return new GcpTransPersonDocumentTypeResponseDTO(entity);
    }

    private void validate(GcpTransPersonDocumentTypeCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
