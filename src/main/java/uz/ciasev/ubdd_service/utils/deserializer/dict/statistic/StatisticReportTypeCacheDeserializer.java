package uz.ciasev.ubdd_service.utils.deserializer.dict.statistic;

import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReportType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class StatisticReportTypeCacheDeserializer extends AbstractDictDeserializer<StatisticReportType> {

    public StatisticReportTypeCacheDeserializer(DictionaryService<StatisticReportType> service) {
        super(StatisticReportType.class, service);
    }
}
