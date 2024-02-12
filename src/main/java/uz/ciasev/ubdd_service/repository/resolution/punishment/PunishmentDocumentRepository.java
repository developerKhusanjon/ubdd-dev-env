package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PunishmentDocument;

import java.util.List;

public interface PunishmentDocumentRepository extends JpaRepository<PunishmentDocument, Long> {

    List<PunishmentDocument> findAllByPunishmentId(Long punishmentId);
}
