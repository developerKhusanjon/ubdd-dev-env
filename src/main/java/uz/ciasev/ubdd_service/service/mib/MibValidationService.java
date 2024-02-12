package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDate;

public interface MibValidationService {

    int MIB_SEND_MAX_DAYS = 90;
    int MIB_SEND_MIN_DAYS = 31;
    int MIB_AUTO_SEND_MIN_DAYS = 70;

    static LocalDate minExecutionDateForNotification() {
        return LocalDate.now().minusDays(MIB_SEND_MAX_DAYS - 1);
    }

    static LocalDate maxExecutionDateForNotification() {
        return LocalDate.now().minusDays(MIB_SEND_MIN_DAYS - 5);
    }

    static int getMibSendMaxDays() {
        return MIB_SEND_MAX_DAYS;
    }

    static int getMibAutoSendMinDays() {
        return MIB_AUTO_SEND_MIN_DAYS;
    }

    static int getMibSendMinDays() {
        return MIB_SEND_MIN_DAYS;
    }

    void validateSend(MibExecutionCard card);

    void checkCreateAvailable(User user, Decision decision);

    void checkEditAvailable(User user, MibExecutionCard card);

    void checkSendAvailable(User user, MibExecutionCard card);

    void checkAutoSendPermitOnDecision(User user, Decision decision);
}
