package uz.ciasev.ubdd_service.service.history;

import uz.ciasev.ubdd_service.entity.history.TransDictAdminHistoricAction;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

public interface TransDictionaryAdminHistoryService {

    void register(AbstractTransEntity entity, TransDictAdminHistoricAction action);
}
