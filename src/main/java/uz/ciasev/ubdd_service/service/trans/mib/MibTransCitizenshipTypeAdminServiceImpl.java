package uz.ciasev.ubdd_service.service.trans.mib;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.SimpleTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.mib.MibTransCitizenshipTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransCitizenshipType;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.MibTransCitizenshipTypeRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class MibTransCitizenshipTypeAdminServiceImpl extends AbstractTransEntityCRDService<MibTransCitizenshipType, MibTransCitizenshipTypeCreateRequestDTO>
        implements MibTransCitizenshipTypeAdminService {

    private final MibTransCitizenshipTypeRepository repository;
    private final String subPath = "mib/citizenship-type";
    private final Class<MibTransCitizenshipType> entityClass = MibTransCitizenshipType.class;

    private final TypeReference<MibTransCitizenshipTypeCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public MibTransCitizenshipType create(MibTransCitizenshipTypeCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(MibTransCitizenshipType entity) {
        return new SimpleTransEntityResponseDTO(entity);
    }

    private void validate(MibTransCitizenshipTypeCreateRequestDTO requestDTO) {
        Long internalId = requestDTO.getInternal().getId();
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByInternalId(internalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "internalId", internalId);
        }

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
