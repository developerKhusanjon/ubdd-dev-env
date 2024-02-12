package uz.ciasev.ubdd_service.exception.transfer;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;

import javax.annotation.Nullable;
import java.util.Optional;

public class MibGeographyNotPresent extends DictTransferNotPresent {

    public MibGeographyNotPresent(@Nullable Long regionId, @Nullable Long districtId) {
        super(
                MibTransGeography.class,
                String.format(
                        "internalRegionId=%s, internalDistrictId=%s",
                        regionId,
                        districtId
                )
        );
    }

    public MibGeographyNotPresent(@Nullable Region region, @Nullable District district) {
        this(
                Optional.ofNullable(region).map(Region::getId).orElse(null),
                Optional.ofNullable(district).map(District::getId).orElse(null)
        );
    }
}
