package uz.ciasev.ubdd_service.service.dict.mib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotPresent;
import uz.ciasev.ubdd_service.repository.dict.mib.MibSendStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiStatusDictionaryServiceAbstract;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class MibSendStatusServiceImpl extends SimpleEmiStatusDictionaryServiceAbstract<MibSendStatus>
        implements MibSendStatusService {

    private final String subPath = "mib-send-statuses";

    private final MibSendStatusRepository repository;
    private final Class<MibSendStatus> entityClass = MibSendStatus.class;


    @Override
    public Optional<MibSendStatus> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public MibSendStatus createByCode(String code, String message) {
        MibSendStatus mibSendStatus = new MibSendStatus();
        mibSendStatus.construct(new DictCreateDTOI() {
            public MultiLanguage getName() {return new MultiLanguage(message, message, message);}
            public String getCode() {return code;}
        });

        return repository.save(mibSendStatus);
    }

    @Override
    public MibSendStatus getSuccessfully() {
        return repository.findById(MibSendStatus.SUCCESSFULLY_ID)
                .orElseThrow(() -> new EntityByIdNotPresent(MibSendStatus.class, MibSendStatus.SUCCESSFULLY_ID));
    }

    @Override
    public MibSendStatus getOrCreate(String code, String message) {
        return findByCode(code)
                .orElseGet(() -> createByCode(code, message));

    }
}
