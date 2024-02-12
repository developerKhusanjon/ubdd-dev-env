package uz.ciasev.ubdd_service.service.dict.notification.sms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.sms.SmsDeliveryStatus;
import uz.ciasev.ubdd_service.repository.dict.sms.SmsDeliveryStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiStatusDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class SmsDeliveryStatusServiceImpl extends SimpleEmiStatusDictionaryServiceAbstract<SmsDeliveryStatus>
        implements SmsDeliveryStatusService {

    private final String subPath = "sms-delivery-statuses";

    private final SmsDeliveryStatusRepository repository;
    private final Class<SmsDeliveryStatus> entityClass = SmsDeliveryStatus.class;
}
