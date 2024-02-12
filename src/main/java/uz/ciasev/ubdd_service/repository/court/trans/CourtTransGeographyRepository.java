package uz.ciasev.ubdd_service.repository.court.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;

import java.util.List;
import java.util.Optional;

public interface CourtTransGeographyRepository extends JpaRepository<CourtTransGeography, Long> {

    List<CourtTransGeography> findAllByExternalCountryIdAndExternalRegionIdAndExternalDistrictId(Long externalCountryId,
                                                                                                 Long externalRegionId,
                                                                                                 Long externalDistrictId);

    Optional<CourtTransGeography> findAllByCountryIdAndRegionIdAndDistrictId(Long countryId,
                                                                             Long regionId,
                                                                             Long districtId);
}
