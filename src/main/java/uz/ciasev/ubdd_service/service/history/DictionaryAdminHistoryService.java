package uz.ciasev.ubdd_service.service.history;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;

import java.util.Map;

public interface DictionaryAdminHistoryService {

    @Transactional
    void register(AbstractDict entity, DictAdminHistoricAction action);

    void register(AbstractDict entity, DictAdminHistoricAction action, Map<String, Object> extraDetail);
}
