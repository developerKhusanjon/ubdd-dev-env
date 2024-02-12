package uz.ciasev.ubdd_service.service.main.admcase;

import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseCalculatedMovementResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface CalculatingService {

    AdmCaseCalculatedMovementResponseDTO calculateAdmCaseMovementsByOrgan(User user, Long admCaseId);

    AdmCaseCalculatedMovementResponseDTO calculateAdmCaseMovements(User user, Long admCaseId);

    AdmCaseCalculatedMovementResponseDTO calculateUbddTabletAdmCaseMovements(User user, Long admCaseId);

    void checkSimplified(User user, AdmCase admCase, Protocol protocol);

    boolean isConsideredOrgan(Organ organ, Department department, AdmCase admCase);

    boolean isConsideredUser(User user, AdmCase admCase);

    boolean isConsideredUser(User user, ArticlePart articlePart);

    boolean isConsideredUserForAll(User user, List<ArticlePart> articleParts);

    void checkCanResolveAdmCase(User user, AdmCase admCase);
}
