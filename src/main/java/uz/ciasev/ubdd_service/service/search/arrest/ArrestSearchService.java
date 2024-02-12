package uz.ciasev.ubdd_service.service.search.arrest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestFullListProjection;

import java.util.Map;

@Validated
public interface ArrestSearchService {

    Page<ArrestFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable);
}
