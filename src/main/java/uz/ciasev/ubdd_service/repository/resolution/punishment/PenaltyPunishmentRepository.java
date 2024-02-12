package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;

public interface PenaltyPunishmentRepository extends JpaRepository<PenaltyPunishment, Long> {
}
