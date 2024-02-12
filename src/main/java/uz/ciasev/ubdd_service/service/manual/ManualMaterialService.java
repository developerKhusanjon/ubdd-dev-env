package uz.ciasev.ubdd_service.service.manual;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.LicenseReturningManualMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.PunishmentManualMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.TerminationManualMaterialDTO;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

public interface ManualMaterialService {

    Decision editPunishment(User user, Long decisionId, PunishmentManualMaterialDTO requestDTO);

    Decision terminate(User user, Long decisionId, TerminationManualMaterialDTO requestDTO);

    Decision returnRevokedLicense(User user, Long decisionId, LicenseReturningManualMaterialDTO requestDTO);
}
