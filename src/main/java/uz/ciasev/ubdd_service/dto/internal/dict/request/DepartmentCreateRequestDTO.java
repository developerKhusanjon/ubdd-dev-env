package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.requests.DepartmentCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DepartmentCreateRequestDTO extends DepartmentUpdateRequestDTO implements DepartmentCreateDTOI, DictCreateRequest<Department> {

    @NotNull(message = ErrorCode.ORGAN_ID_REQUIRED)
    @JsonProperty(value = "organId")
    private Organ organ;

    @Override
    public void applyToNew(Department entity) {
        entity.construct(this);
    }
}
