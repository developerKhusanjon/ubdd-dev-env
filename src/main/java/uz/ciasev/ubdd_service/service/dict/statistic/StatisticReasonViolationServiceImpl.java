package uz.ciasev.ubdd_service.service.dict.statistic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReasonViolation;
import uz.ciasev.ubdd_service.repository.dict.statistic.StatisticReasonViolationRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class StatisticReasonViolationServiceImpl extends SimpleEmiDictionaryServiceAbstract<StatisticReasonViolation>
        implements StatisticReasonViolationService {

    private final String subPath = "statistic-reason-violation";

    private final StatisticReasonViolationRepository repository;
    private final Class<StatisticReasonViolation> entityClass = StatisticReasonViolation.class;
}
