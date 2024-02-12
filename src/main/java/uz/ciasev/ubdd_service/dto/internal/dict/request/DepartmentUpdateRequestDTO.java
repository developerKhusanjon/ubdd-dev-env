package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.requests.DepartmentUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DepartmentUpdateRequestDTO extends BaseDictRequestDTO implements DepartmentUpdateDTOI, DictUpdateRequest<Department> {

    @Override
    public void applyToOld(Department entity) {
        entity.update(this);
    }
}
