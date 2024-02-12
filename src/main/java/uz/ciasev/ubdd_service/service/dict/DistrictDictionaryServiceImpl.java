package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DistrictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DistrictUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DistrictListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DistrictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.repository.dict.DistrictRepository;
import uz.ciasev.ubdd_service.service.ConvertDTOService;
import uz.ciasev.ubdd_service.service.settings.OrganInfoAutoCreateService;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class DistrictDictionaryServiceImpl extends AbstractDictionaryCRUDService<District, DistrictCreateRequestDTO, DistrictUpdateRequestDTO>
        implements DistrictDictionaryService {

    private final Long unknownId = 999L;

    private final String subPath = "districts";
    private final TypeReference<DistrictCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<DistrictUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final ConvertDTOService convertDTOService;
    private final DistrictRepository repository;
    private final Class<District> entityClass = District.class;

    private final OrganInfoAutoCreateService organInfoService;

    @Override
    @Transactional(timeout = 120)
    public List<District> create(List<DistrictCreateRequestDTO> request) {
        List<District> districts = super.create(request);
        districts.stream().map(organInfoService::createForNew).collect(Collectors.toList());

        return districts;
    }

    @Override
    @Transactional(timeout = 120)
    public District create(DistrictCreateRequestDTO request) {
        District district = super.create(request);
        organInfoService.createForNew(district);

        return district;
    }

    public DistrictResponseDTO buildResponseDTO(District district) {
        return new DistrictResponseDTO(district);
    }

    public DistrictListResponseDTO buildListResponseDTO(District district) {
        return new DistrictListResponseDTO(district);
    }

    @Override
    public List<DistrictResponseDTO> findAllMibPresence() {
        return repository
                .findAllMibPresence()
                .stream()
                .map(DistrictResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DistrictResponseDTO> findAllCourtPresence() {
        return repository
                .findAllCourtPresence()
                .stream()
                .map(DistrictResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<District> findByIdMibPresence(Long id) {
        return repository.findByIdMibPresence(id);
    }

    public Optional<District> findByIdCourtPresence(Long id) {
        return repository.findByIdCourtPresence(id);
    }

    @Override
    public List<District> findAllByRegion(Region region) {
        return repository.findAll(DictionarySpecifications.withRegionId(region.getId()));
    }
}
