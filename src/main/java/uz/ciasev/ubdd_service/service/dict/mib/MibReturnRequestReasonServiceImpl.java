package uz.ciasev.ubdd_service.service.dict.mib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.repository.dict.mib.MibReturnRequestReasonRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;


@Service
@RequiredArgsConstructor
@Getter
public class MibReturnRequestReasonServiceImpl extends SimpleEmiDictionaryServiceAbstract<MibReturnRequestReason>
        implements MibReturnRequestReasonService {
    private final String subPath = "mib-return-request-reasons";

    private final MibReturnRequestReasonRepository repository;
    private final Class<MibReturnRequestReason> entityClass = MibReturnRequestReason.class;
}
