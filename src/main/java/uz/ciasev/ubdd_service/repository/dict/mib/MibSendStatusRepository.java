package uz.ciasev.ubdd_service.repository.dict.mib;

import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.Optional;

public interface MibSendStatusRepository extends AbstractDictRepository<MibSendStatus> {

    Optional<MibSendStatus> findByCode(String code);
}
