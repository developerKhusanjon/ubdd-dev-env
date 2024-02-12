package uz.ciasev.ubdd_service.service.trans.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransDistrictResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransDistrictCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransDistrictRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class CourtTransDistrictAdminServiceImpl extends AbstractTransEntityCRDService<CourtTransDistrict, CourtTransDistrictCreateRequestDTO>
        implements CourtTransDistrictAdminService {


    private final CourtTransDistrictRepository repository;
    private final String subPath = "court/district";
    private final Class<CourtTransDistrict> entityClass = CourtTransDistrict.class;

    private final TypeReference<CourtTransDistrictCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public CourtTransDistrict create(CourtTransDistrictCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(CourtTransDistrict entity) {
        return new CourtTransDistrictResponseDTO(entity);
    }

    private void validate(CourtTransDistrictCreateRequestDTO requestDTO) {
        Long externalId = requestDTO.getExternalId();

        if (repository.findByExternalId(externalId).isPresent()) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
