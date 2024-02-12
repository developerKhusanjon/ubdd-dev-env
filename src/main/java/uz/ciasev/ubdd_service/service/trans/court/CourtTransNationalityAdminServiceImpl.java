package uz.ciasev.ubdd_service.service.trans.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.SimpleTransEntityResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransNationalityCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransNationality;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.CourtTransNationalityAdminRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class CourtTransNationalityAdminServiceImpl extends AbstractTransEntityCRDService<CourtTransNationality, CourtTransNationalityCreateRequestDTO>
        implements CourtTransNationalityAdminService {

    private final CourtTransNationalityAdminRepository repository;
    private final String subPath = "court/nationality";
    private final Class<CourtTransNationality> entityClass = CourtTransNationality.class;

    private final TypeReference<CourtTransNationalityCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public CourtTransNationality create(CourtTransNationalityCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(CourtTransNationality entity) {
        return new SimpleTransEntityResponseDTO(entity);
    }

    private void validate(CourtTransNationalityCreateRequestDTO requestDTO) {
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
