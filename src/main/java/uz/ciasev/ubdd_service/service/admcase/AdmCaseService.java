package uz.ciasev.ubdd_service.service.admcase;

import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

public interface AdmCaseService {

    AdmCase getById(Long id);

    AdmCase getByProtocolId(Long id);

    AdmCase createEmptyAdmCase(Inspector user, Organ organ, Department department, Region region, District district);

    AdmCase createCourtCopyAdmCase(AdmCase admCase, Long claimId);

    AdmCase createCopyAdmCase(User user, AdmCase admCase);

    AdmCase update(Long id, AdmCase admCase);

    AdmCase getByViolator(Violator violator);

    AdmCase findByJuridic(Juridic juridic);

    AdmCase getByIdAndClaimId(Long id, Long claimId);

    AdmCase getByClaimId(Long claimMergeId);
}
