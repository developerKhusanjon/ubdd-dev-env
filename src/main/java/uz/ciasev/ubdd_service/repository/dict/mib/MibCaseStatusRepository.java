package uz.ciasev.ubdd_service.repository.dict.mib;

import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.Optional;

public interface MibCaseStatusRepository extends AbstractDictRepository<MibCaseStatus> {

    Optional<MibCaseStatus> findByCode(String code);
}
