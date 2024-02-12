package uz.ciasev.ubdd_service.service.mib;

import java.util.List;
import java.util.Map;

public interface MibAutoSendService {

    void sendList(List<Long> decisions);

    long sendListAndCount(Map<Long, String> decisions);

    void send(Long decision);
}
