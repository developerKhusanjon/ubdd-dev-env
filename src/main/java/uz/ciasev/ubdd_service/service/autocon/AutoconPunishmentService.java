package uz.ciasev.ubdd_service.service.autocon;

import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import java.util.List;

public interface AutoconPunishmentService {

    List<Long> getNextNPunishmentsForAddToAutocon(int n);

    void addPunishmentToAutocon(Long punishmentId);

    void deletePunishmentFromAutocon(Punishment punishment);
}
