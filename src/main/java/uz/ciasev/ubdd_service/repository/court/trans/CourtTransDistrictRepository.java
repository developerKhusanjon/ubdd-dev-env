package uz.ciasev.ubdd_service.repository.court.trans;

import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.repository.trans.AbstractTransEntityRepository;

import java.util.Optional;

public interface CourtTransDistrictRepository extends AbstractTransEntityRepository<CourtTransDistrict> {

    Optional<CourtTransDistrict> findByExternalId(Long externalId);
}
