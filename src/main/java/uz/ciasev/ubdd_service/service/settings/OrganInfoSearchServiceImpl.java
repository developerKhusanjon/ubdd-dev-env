package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.settings.OrganInfoRepository;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganInfoSearchServiceImpl implements OrganInfoSearchService {

    private final OrganInfoRepository repository;
    private final FilterHelper<OrganInfo> filterHelper;

    @Override
    public Page<OrganInfo> findAll(Map<String, String> filters, Pageable pageable) {
        return repository.findAll(filterHelper.getParamsSpecification(filters), pageable);
    }

    @Override
    public OrganInfo getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(OrganInfo.class, id));
    }

    @Override
    public Optional<OrganInfo> findByPlace(Place place) {
        return repository.findByOrganAndDepartmentAndRegionAndDistrict(place.getOrgan(), place.getDepartment(), place.getRegion(), place.getDistrict());
    }
}
