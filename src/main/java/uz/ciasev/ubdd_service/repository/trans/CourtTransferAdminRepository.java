package uz.ciasev.ubdd_service.repository.trans;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

public interface CourtTransferAdminRepository extends AbstractTransEntityRepository<CourtTransfer> {

    boolean existsByRegionAndDistrict(Region region, District district);

    boolean existsByExternalId(Long externalId);
}
