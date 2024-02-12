package uz.ciasev.ubdd_service.service.autocon;

import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import java.util.Optional;

public interface AutoconSendingService {

    void create(Punishment punishment);

    /**
     * TO USE WITH EVENTS
     * WHEN PUNISHMENT IS CLOSED:
     * - PAYMENTS
     * - RESOLUTION CLOSE
     */
    void prepareClose(AutoconSending sending);

    void closed(AutoconSending sending);

    void opened(AutoconSending sending);

    AutoconSending getById(Long id);

    Optional<AutoconSending> findByPunishmentId(Long punishmentId);
}
