package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.entity.court.BillingSending;

import java.util.List;

public interface BillingSendingService {

    void save(BillingSending billingSending);

    void saveAll(List<BillingSending> list);

    List<BillingSending> findNextInOrder();

    void handle(BillingSending sending);
}
