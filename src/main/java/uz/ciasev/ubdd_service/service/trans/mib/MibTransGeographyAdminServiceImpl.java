package uz.ciasev.ubdd_service.service.trans.mib;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.mib.MibTransGeographyResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.mib.MibTransGeographyCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.mib.trans.MibTransGeographyAdminRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Service
public class MibTransGeographyAdminServiceImpl extends AbstractTransEntityCRDService<MibTransGeography, MibTransGeographyCreateRequestDTO>
        implements MibTransGeographyAdminService {

    private final MibTransGeographyAdminRepository repository;
    private final String subPath = "mib/geography";
    private final Class<MibTransGeography> entityClass = MibTransGeography.class;

    private final TypeReference<MibTransGeographyCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public MibTransGeography create(MibTransGeographyCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(MibTransGeography entity) {
        return new MibTransGeographyResponseDTO(entity);
    }

    private void validate(MibTransGeographyCreateRequestDTO requestDTO) {
        Region region = requestDTO.getRegion();
        District district = requestDTO.getDistrict();
        boolean isUniqueInternal = Optional.ofNullable(requestDTO.getIsAvailableForSendMibExecutionCard()).orElse(false);

        Long externalId = requestDTO.getExternalId();
        boolean isUniqueExternal = Optional.ofNullable(requestDTO.getIsAvailableForMibProtocolRegistration()).orElse(false);

        if (district == null && repository.entityIsPresentByRegionAndEmptyDistrict(region)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "regionId", region.getId(),
                                                  "districtId", null);
        }

        if (repository.existsByRegionAndDistrictAndExternalId(region, district, externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "regionId", region.getId(),
                                                  "districtId", Optional.ofNullable(district).map(District::getId).orElse(null),
                                                  "externalId", externalId);
        }

        if (isUniqueInternal && repository.entityIsPresentByInternalParams(region, district)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "regionId", region.getId(),
                                                  "districtId", Optional.ofNullable(district).map(District::getId).orElse(null),
                                                  "isAvailableForSendMibExecutionCard", isUniqueInternal);
        }

        if (isUniqueExternal && repository.entityIsPresentByExternalParams(externalId)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "externalId", externalId,
                                                  "isAvailableForMibProtocolRegistration", isUniqueExternal);
        }
    }
}
