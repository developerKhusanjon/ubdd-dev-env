package uz.ciasev.ubdd_service.service.trans.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransGeographyCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransGeographyResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.CourtTransGeographyAdminRepository;
import uz.ciasev.ubdd_service.service.address.AddressValidationService;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Service
public class CourtTransGeographyAdminServiceImpl extends AbstractTransEntityCRDService<CourtTransGeography, CourtTransGeographyCreateRequestDTO>
        implements CourtTransGeographyAdminService {

    private final AddressValidationService addressValidationService;

    private final CourtTransGeographyAdminRepository repository;
    private final String subPath = "court/geography";
    private final Class<CourtTransGeography> entityClass = CourtTransGeography.class;

    private final TypeReference<CourtTransGeographyCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public CourtTransGeography create(CourtTransGeographyCreateRequestDTO requestDTO) {
        addressValidationService.validate(requestDTO);
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(CourtTransGeography entity) {
        return new CourtTransGeographyResponseDTO(entity);
    }

    private void validate(CourtTransGeographyCreateRequestDTO requestDTO) {
        Country country = requestDTO.getCountry();
        Region region = requestDTO.getRegion();
        District district = requestDTO.getDistrict();

        if (repository.existsByCountryAndRegionAndDistrict(country, region, district)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "countryId", country.getId(),
                                                  "regionId", Optional.ofNullable(region).map(Region::getId).orElse(null),
                                                  "districtId", Optional.ofNullable(district).map(District::getId).orElse(null));
        }
    }
}
