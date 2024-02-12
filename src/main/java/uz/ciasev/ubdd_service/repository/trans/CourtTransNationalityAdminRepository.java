package uz.ciasev.ubdd_service.repository.trans;

import uz.ciasev.ubdd_service.entity.trans.court.CourtTransNationality;

public interface CourtTransNationalityAdminRepository extends AbstractSimpleTransEntityRepository<CourtTransNationality> {
    boolean existsByExternalId(Long externalId);
}