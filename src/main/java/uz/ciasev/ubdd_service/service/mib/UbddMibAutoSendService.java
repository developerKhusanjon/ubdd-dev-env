package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;

import java.util.List;
import java.util.Map;

public interface UbddMibAutoSendService {

    void send(Long decisionId, MibResult mibResult);
}
