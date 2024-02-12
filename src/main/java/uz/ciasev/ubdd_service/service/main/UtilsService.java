package uz.ciasev.ubdd_service.service.main;

import uz.ciasev.ubdd_service.entity.user.User;

public interface UtilsService {

    void generateInvoiceForOld(User user, Long decisionId);

}
