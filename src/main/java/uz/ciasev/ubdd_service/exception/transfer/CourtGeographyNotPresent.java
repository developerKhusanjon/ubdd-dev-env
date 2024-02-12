package uz.ciasev.ubdd_service.exception.transfer;

import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;

import javax.annotation.Nullable;

public class CourtGeographyNotPresent extends DictTransferNotPresent {

    public CourtGeographyNotPresent(@Nullable Long internalCountryId, @Nullable Long internalRegionId, @Nullable Long internalDistrictId) {
        super(
                CourtTransGeography.class,
                String.format(
                        "internalCountryId=%s, internalRegionId=%s, internalDistrictId=%s",
                        internalCountryId,
                        internalRegionId,
                        internalDistrictId
                )
        );
    }
}
