package uz.ciasev.ubdd_service.service.court.trans;

import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;

import java.util.Optional;

public interface CourtTransGeographyService {

    CourtTransDistrict getCourtDistrictByExternalId(Long externalId);

    CourtTransGeography getByExternal(Long externalCountryId, Long externalRegionId, Long externalDistrictId);

    Optional<CourtTransGeography> findByExternal(Long externalCountryId, Long externalRegionId, Long externalDistrictId);

    CourtTransGeography getByInternal(Country country, Region region, District district);

    Optional<CourtTransGeography> findByInternal(Country country, Region region, District district);

    CourtTransGeography getByInternal(Long internalCountryId, Long internalRegionId, Long internalDistrictId);

    Optional<CourtTransGeography> findByInternal(Long internalCountryId, Long internalRegionId, Long internalDistrictId);

    CourtTransGeography getByInternal(Address address);

}
