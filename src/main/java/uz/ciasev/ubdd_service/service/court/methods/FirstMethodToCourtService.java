package uz.ciasev.ubdd_service.service.court.methods;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

public interface FirstMethodToCourtService {

    AdmCase sendAdmCaseToCourt(User user, Long id);

    AdmCase sendAdmCaseToCourt308(User user, Long id);
}
