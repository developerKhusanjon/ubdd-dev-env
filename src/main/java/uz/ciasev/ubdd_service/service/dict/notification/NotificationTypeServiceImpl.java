package uz.ciasev.ubdd_service.service.dict.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.NotificationType;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.NotificationTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class NotificationTypeServiceImpl extends SimpleBackendDictionaryServiceAbstract<NotificationType, NotificationTypeAlias>
        implements NotificationTypeDictionaryService {

    private final String subPath = "notification-types";

    private final NotificationTypeRepository repository;
    private final Class<NotificationType> entityClass = NotificationType.class;

    @Override
    public Class<NotificationTypeAlias> getAliasClass() {
        return NotificationTypeAlias.class;
    }
}
