package uz.ciasev.ubdd_service.service.resolution.punishment;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.PunishmentDocumentResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PunishmentDocument;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface PunishmentDocumentService {

    PunishmentDocument findById(Long id);
    void save(User user, Long punishmentId, PunishmentDocumentRequestDTO dto);
    void update(User user, Long id, PunishmentDocumentRequestDTO dto);
    PunishmentDocument delete(User user, Long id);
    List<PunishmentDocument> findByPunishmentId(User user, Long id);
    List<PunishmentDocumentResponseDTO> findDTOByPunishmentId(User user, Long id);
}
