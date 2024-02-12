package uz.ciasev.ubdd_service.repository.dict.mib;

import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.Optional;

public interface MibReturnRequestReasonRepository extends AbstractDictRepository<MibReturnRequestReason> {

    Optional<MibReturnRequestReason> findByCode(String code);
}
