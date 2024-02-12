package uz.ciasev.ubdd_service.repository.trans;

import uz.ciasev.ubdd_service.entity.trans.mib.MibTransCitizenshipType;

public interface MibTransCitizenshipTypeRepository extends AbstractSimpleTransEntityRepository<MibTransCitizenshipType> {

    boolean existsByExternalId(Long externalId);
}
