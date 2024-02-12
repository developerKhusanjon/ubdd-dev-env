package uz.ciasev.ubdd_service.repository.autocon;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.trans.autocon.AutoconTransRegion;

import java.util.Optional;

public interface AutoconRegionRepository extends JpaRepository<AutoconTransRegion, Long> {

    Optional<AutoconTransRegion> findByInternalId(Long regionId);
}
