package uz.ciasev.ubdd_service.utils.deserializer.dict.statistic;

import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReasonViolation;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class StatisticReasonViolationCacheDeserializer extends AbstractDictDeserializer<StatisticReasonViolation> {

    public StatisticReasonViolationCacheDeserializer(DictionaryService<StatisticReasonViolation> service) {
        super(StatisticReasonViolation.class, service);
    }
}
