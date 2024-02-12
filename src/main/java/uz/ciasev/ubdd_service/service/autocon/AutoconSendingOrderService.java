package uz.ciasev.ubdd_service.service.autocon;

import java.util.List;

public interface AutoconSendingOrderService {

    List<Long> getNextForOpen(Long n);

    List<Long> getNextForClose(Long n);

    void handle(Long sendingId);
}
