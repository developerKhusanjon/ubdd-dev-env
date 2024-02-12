package uz.ciasev.ubdd_service.service.main.migration;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import java.util.List;

public interface MainService {

    @Transactional
    void courtMergeAdmCases(Long fromCaseId, Long toCaseId);

    @Transactional
    List<Violator> organMergeAdmCases(@Inspector User user, Long fromCaseId, Long toCaseId);

    @Transactional
    AdmCase courtSeparateAdmCase(Long caseId, List<Long> persons, Long claimId);
}
