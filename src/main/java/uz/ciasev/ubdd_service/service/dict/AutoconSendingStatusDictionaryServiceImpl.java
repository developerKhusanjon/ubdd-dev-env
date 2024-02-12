package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatus;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;
import uz.ciasev.ubdd_service.repository.dict.AutoconSendingStatusRepository;

@Service
@RequiredArgsConstructor
@Getter
public class AutoconSendingStatusDictionaryServiceImpl extends SimpleBackendStatusDictionaryServiceAbstract<AutoconSendingStatus, AutoconSendingStatusAlias>
        implements AutoconSendingStatusDictionaryService {

    private final String subPath = "autocon-sending-statuses";

    private final Class<AutoconSendingStatus> entityClass = AutoconSendingStatus.class;
    private final AutoconSendingStatusRepository repository;

    @Override
    public Class<AutoconSendingStatusAlias> getAliasClass() {
        return AutoconSendingStatusAlias.class;
    }
}
