package uz.ciasev.ubdd_service.repository.mib.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;

import java.util.Optional;

public interface MibTransGeographyRepository extends JpaRepository<MibTransGeography, Long> {

    Optional<MibTransGeography> findByRegionIdAndDistrictId(Long regionId, Long districtId);
}
