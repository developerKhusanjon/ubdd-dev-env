package uz.ciasev.ubdd_service.service.notification;

import uz.ciasev.ubdd_service.entity.user.User;

public interface DecisionMailNotificationService {

    void sendDecisionToMail(User user, Long decisionId);

    void sendMibPresentToMail(User user, Long decisionId);
}
