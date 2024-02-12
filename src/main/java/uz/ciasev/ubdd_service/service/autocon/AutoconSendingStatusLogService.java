package uz.ciasev.ubdd_service.service.autocon;

import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;

import java.util.List;

public interface AutoconSendingStatusLogService {

    void save(AutoconSending sending);
    void saveAll(List<AutoconSending> sendingList);
}
