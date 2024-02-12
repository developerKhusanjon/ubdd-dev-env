package uz.ciasev.ubdd_service.service.dict.notification.mail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mail.MailDeliveryStatus;
import uz.ciasev.ubdd_service.repository.dict.mail.MailDeliveryStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdStatusDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class MailDeliveryStatusServiceImpl extends SimpleExternalIdStatusDictionaryServiceAbstract<MailDeliveryStatus>
        implements MailDeliveryStatusService {

    private final String subPath = "mail-delivery-statuses";

    private final MailDeliveryStatusRepository repository;
    private final Class<MailDeliveryStatus> entityClass = MailDeliveryStatus.class;
}
