package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.utils.validator.ConsistOrganDepartment;

@ConsistOrganDepartment
public interface OrganDepartmentRequest {

    Organ getOrgan();

    Department getDepartment();
}
