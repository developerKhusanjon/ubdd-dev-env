package uz.ciasev.ubdd_service.repository.mib.trans;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;
import uz.ciasev.ubdd_service.repository.trans.AbstractTransEntityRepository;

import javax.annotation.Nullable;

public interface MibTransGeographyAdminRepository extends AbstractTransEntityRepository<MibTransGeography> {

    boolean existsByRegionAndDistrictAndIsAvailableForSendMibExecutionCard(Region region, @Nullable District district, boolean isAvailableForSendMibExecutionCard);

    boolean existsByExternalIdAndIsAvailableForMibProtocolRegistration(Long externalId, boolean isAvailableForMibProtocolRegistration);

    boolean existsByRegionAndDistrictAndExternalId(Region region, @Nullable District district, Long externalId);

    boolean existsByRegionAndDistrict(Region region, @Nullable District district);

    default boolean entityIsPresentByInternalParams(Region region, @Nullable District district) {
        return existsByRegionAndDistrictAndIsAvailableForSendMibExecutionCard(region, district, true);
    }

    default boolean entityIsPresentByExternalParams(Long externalId) {
        return existsByExternalIdAndIsAvailableForMibProtocolRegistration(externalId, true);
    }

    default boolean entityIsPresentByRegionAndEmptyDistrict(Region region) {
        return existsByRegionAndDistrict(region, null);
    }
}
