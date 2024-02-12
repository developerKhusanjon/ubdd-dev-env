package uz.ciasev.ubdd_service.service.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;

import java.util.Map;
import java.util.Optional;

public interface OrganInfoSearchService {

    Page<OrganInfo> findAll(Map<String, String> filters, Pageable pageable);

    OrganInfo getById(Long id);

    Optional<OrganInfo> findByPlace(Place place);
}
