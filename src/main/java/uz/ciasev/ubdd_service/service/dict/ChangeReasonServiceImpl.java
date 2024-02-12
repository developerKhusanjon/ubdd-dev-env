package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;
import uz.ciasev.ubdd_service.repository.dict.ChangeReasonRepository;

@Service
@RequiredArgsConstructor
@Getter
public class ChangeReasonServiceImpl extends SimpleEmiDictionaryServiceAbstract<ChangeReasonType>
        implements ChangeReasonTypeService {
    private final String subPath = "change-reason-types";

    private final ChangeReasonRepository repository;
    public Class<ChangeReasonType> entityClass = ChangeReasonType.class;
}
