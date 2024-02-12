package uz.ciasev.ubdd_service.service.dict.violation_event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventAnnulmentReason;
import uz.ciasev.ubdd_service.repository.dict.violation_event.ViolationEventAnnulmentReasonRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ViolationEventAnnulmentReasonDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<ViolationEventAnnulmentReason>
        implements ViolationEventAnnulmentReasonDictionaryService {

    private final String subPath = "violation-event-annulment-reasons";

    private final ViolationEventAnnulmentReasonRepository repository;
    private final Class<ViolationEventAnnulmentReason> entityClass = ViolationEventAnnulmentReason.class;
}
