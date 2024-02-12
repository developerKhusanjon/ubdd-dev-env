package uz.ciasev.ubdd_service.repository.trans;

import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;

public interface CourtTransGeographyAdminRepository extends AbstractTransEntityRepository<CourtTransGeography> {

    boolean existsByCountryAndRegionAndDistrict(Country country, Region region, District district);
}
