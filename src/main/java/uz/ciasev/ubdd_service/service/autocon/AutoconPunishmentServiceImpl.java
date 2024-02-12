package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutoconPunishmentServiceImpl implements AutoconPunishmentService {

    private final PunishmentRepository punishmentRepository;
    private final PunishmentService punishmentService;
    private final AutoconSendingService autoconSendingService;


    @Override
    public List<Long> getNextNPunishmentsForAddToAutocon(int n) {
        return punishmentRepository.punishmentsToSendToAutocon(
                        LocalDate.now().minusDays(30),
                        PageUtils.limit(n)
                );
    }

    @Override
    public void addPunishmentToAutocon(Long punishmentId) {
        Punishment punishment = punishmentService.getById(punishmentId);

        if (punishment.getStatus().is(AdmStatusAlias.EXECUTED)) {
            return;
        }

        if (isNotAvailableForAutocon(punishment)) {
            throw new LogicalException(String.format("Punishment %s not available for send to autocon", punishment.getId()));
        }

        autoconSendingService.create(punishment);
    }

    @Override
    public void deletePunishmentFromAutocon(Punishment punishment) {
        if (isNotAvailableForAutocon(punishment)) {
            return;
        }

        Optional<AutoconSending> sendingOpt = autoconSendingService.findByPunishmentId(punishment.getId());

        sendingOpt
                .ifPresent(autoconSendingService::prepareClose);
    }

    private boolean isNotAvailableForAutocon(Punishment punishment) {
        if (punishment.getType().not(PunishmentTypeAlias.PENALTY)) {
            return true;
        }

        return false;
    }
}
