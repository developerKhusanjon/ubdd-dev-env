package uz.ciasev.ubdd_service.service.court.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;
import uz.ciasev.ubdd_service.exception.transfer.CourtGeographyNotPresent;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransGeographyRepository;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransDistrictRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtTransGeographyServiceImpl implements CourtTransGeographyService {

    private final CourtTransGeographyRepository courtGeographyRepository;
    private final CourtTransDistrictRepository courtDistrictRepository;

    @Override
    public CourtTransDistrict getCourtDistrictByExternalId(Long externalId) {
        return courtDistrictRepository.findByExternalId(externalId)
                .orElseThrow(() -> new DictTransferNotPresent(CourtTransDistrict.class, "externalId", externalId));
    }

    @Override
    public CourtTransGeography getByExternal(Long externalCountryId, Long externalRegionId, Long externalDistrictId) {
        return findByExternal(externalCountryId, externalRegionId, externalDistrictId)
                .orElseThrow(() -> new DictTransferNotPresent(CourtTransGeography.class,
                        "externalCountryId", externalCountryId,
                        "externalRegionId", externalRegionId,
                        "externalDistrictId", externalDistrictId
                ));
    }


    @Override
    public Optional<CourtTransGeography> findByExternal(Long externalCountryId, Long externalRegionId, Long
            externalDistrictId) {
        return courtGeographyRepository.findAllByExternalCountryIdAndExternalRegionIdAndExternalDistrictId(
                externalCountryId,
                externalRegionId,
                externalDistrictId
        ).stream().findFirst();
    }


    @Override
    public CourtTransGeography getByInternal(Country country, Region region, District district) {
        return getByInternal(
                Optional.ofNullable(country).map(Country::getId).orElse(null),
                Optional.ofNullable(region).map(Region::getId).orElse(null),
                Optional.ofNullable(district).map(District::getId).orElse(null)
        );
    }

    @Override
    public Optional<CourtTransGeography> findByInternal(Country country, Region region, District district) {
        return findByInternal(
                Optional.ofNullable(country).map(Country::getId).orElse(null),
                Optional.ofNullable(region).map(Region::getId).orElse(null),
                Optional.ofNullable(district).map(District::getId).orElse(null)
        );
    }

    @Override
    public CourtTransGeography getByInternal(Long internalCountryId, Long internalRegionId, Long internalDistrictId) {
        return findByInternal(internalCountryId, internalRegionId, internalDistrictId)
                .orElseThrow(() -> new CourtGeographyNotPresent(internalCountryId, internalRegionId, internalDistrictId));
    }

    @Override
    public Optional<CourtTransGeography> findByInternal(Long internalCountryId, Long internalRegionId, Long internalDistrictId) {
        return courtGeographyRepository.findAllByCountryIdAndRegionIdAndDistrictId(internalCountryId, internalRegionId, internalDistrictId);
    }

    @Override
    public CourtTransGeography getByInternal(Address address) {
        return getByInternal(address.getCountryId(), address.getRegionId(), address.getDistrictId());
    }
}
