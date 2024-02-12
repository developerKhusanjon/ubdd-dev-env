package uz.ciasev.ubdd_service.service.dict.mib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationType;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.mib.MibNotificationTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@AllArgsConstructor
@Getter
public class MibNotificationTypeServiceImpl extends SimpleBackendDictionaryServiceAbstract<MibNotificationType, MibNotificationTypeAlias>
        implements MibNotificationTypeService {

    private final String subPath = "mib-notification-types";
    private final Class<MibNotificationType> entityClass = MibNotificationType.class;

    private final MibNotificationTypeRepository repository;

    @Override
    public Class<MibNotificationTypeAlias> getAliasClass() {
        return MibNotificationTypeAlias.class;
    }
}
