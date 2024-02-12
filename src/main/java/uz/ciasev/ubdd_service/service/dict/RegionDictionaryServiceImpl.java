package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.dict.request.RegionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.RegionResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.repository.dict.RegionRepository;
import uz.ciasev.ubdd_service.service.settings.OrganInfoAutoCreateService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class RegionDictionaryServiceImpl extends AbstractDictionaryCRUDService<Region, RegionRequestDTO, RegionRequestDTO>
        implements RegionDictionaryService {

    private final OrganInfoAutoCreateService organInfoService;

    private final Long unknownId = 999L;

    private final String subPath = "regions";
    private final TypeReference<RegionRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<RegionRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final RegionRepository repository;
    private final Class<Region> entityClass = Region.class;

    @Override
    public RegionResponseDTO buildResponseDTO(Region entity) {
        return new RegionResponseDTO(entity);
    }

    @Override
    @Transactional(timeout = 60)
    public List<Region> create(List<RegionRequestDTO> request) {
        List<Region> regions = super.create(request);
        regions.stream().map(organInfoService::createForNew).collect(Collectors.toList());

        return regions;
    }

    @Override
    @Transactional(timeout = 60)
    public Region create(RegionRequestDTO request) {
        Region region = super.create(request);
        organInfoService.createForNew(region);

        return region;
    }

    @Override
    public List<RegionResponseDTO> findAllMibPresence() {
        return repository
                .findAllMibPresence()
                .stream()
                .map(RegionResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Region> findByIdMibPresence(Long id) {
        return repository.findByIdMibPresence(id);
    }

    @Override
    public Optional<Region> findByIdCourtPresence(Long id) {
//        return regionRepository.findByIdCourtPresence(id);
        throw new NotImplementedException("Должна быть проверка по справочнику CourtTransfer");
    }
}
