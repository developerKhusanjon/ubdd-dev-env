package uz.ciasev.ubdd_service.service.trans.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransferResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransferCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.CourtTransferAdminRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Service
public class CourtTransferAdminServiceImpl extends AbstractTransEntityCRDService<CourtTransfer, CourtTransferCreateRequestDTO>
        implements CourtTransferAdminService {


    private final CourtTransferAdminRepository repository;
    private final String subPath = "court/transfer";
    private final Class<CourtTransfer> entityClass = CourtTransfer.class;

    private final TypeReference<CourtTransferCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public CourtTransfer create(CourtTransferCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(CourtTransfer entity) {
        return new CourtTransferResponseDTO(entity);
    }

    private void validate(CourtTransferCreateRequestDTO requestDTO) {
        Region region = requestDTO.getRegion();
        District district = requestDTO.getDistrict();
        Long externalId = requestDTO.getExternalId();

        if (repository.existsByRegionAndDistrict(region, district)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "regionId", region.getId(),
                                                  "districtId", Optional.ofNullable(district).map(District::getId).orElse(null));
        }

        if (repository.existsByExternalId(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass, "externalId", externalId);
        }
    }
}
