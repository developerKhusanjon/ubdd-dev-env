package uz.ciasev.ubdd_service.service.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;

@Service
@RequiredArgsConstructor
public class EntityViolatorService {

    private final PunishmentRepository punishmentRepository;
    private final CompensationRepository compensationRepository;

    public Long idBy(Punishment punishment) {
        return punishmentRepository.findViolatorIdByPunishment(punishment);
    }

    public Long idBy(Compensation compensation) {
        return compensationRepository.findViolatorIdByCompensation(compensation);
    }
}
