package uz.ciasev.ubdd_service.service.dict.statistic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReportType;
import uz.ciasev.ubdd_service.repository.dict.statistic.StatisticReportTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class StatisticReportTypeServiceImpl extends SimpleEmiDictionaryServiceAbstract<StatisticReportType>
        implements StatisticReportTypeService {

    private final String subPath = "statistic-report-types";

    private final StatisticReportTypeRepository repository;
    private final Class<StatisticReportType> entityClass = StatisticReportType.class;
}
