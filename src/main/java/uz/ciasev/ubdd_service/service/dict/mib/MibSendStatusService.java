package uz.ciasev.ubdd_service.service.dict.mib;

import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiStatusDictionaryService;

import java.util.Optional;

public interface MibSendStatusService extends SimpleEmiStatusDictionaryService<MibSendStatus> {

    Optional<MibSendStatus> findByCode(String code);

    MibSendStatus createByCode(String code, String message);

    MibSendStatus getSuccessfully();

    MibSendStatus getOrCreate(String code, String message);
}
