package uz.ciasev.ubdd_service.entity.violator;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ViolatorCitizenListProjection {

    Long getId();

    String getPinpp();

    String getAdmCaseNumber();

    LocalDate getAdmCaseOpenedDate();

    LocalDateTime getAdmCaseConsideredTime();

    LocalDateTime getAdmCaseCreatedTime();

    AdmStatus getAdmCaseStatus();

    AdmStatus getDecisionStatus();

    Organ getAdmCaseOrgan();

    Department getAdmCaseDepartment();

    District getAdmCaseDistrict();

    Region getAdmCaseRegion();
}
