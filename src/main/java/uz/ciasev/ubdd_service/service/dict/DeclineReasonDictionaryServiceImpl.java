package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.admcase.DeclineReason;
import uz.ciasev.ubdd_service.repository.dict.DeclineReasonRepository;

@Getter
@Service
@RequiredArgsConstructor
public class DeclineReasonDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<DeclineReason>
        implements DeclineReasonDictionaryService {

    private final String subPath = "decline-reasons";

    private final Class<DeclineReason> entityClass = DeclineReason.class;
    private final DeclineReasonRepository repository;

}
