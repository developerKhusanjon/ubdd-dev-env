package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Department;

@Getter
public class DepartmentResponseDTO extends DictResponseDTO {
    private final Long organ;

    public DepartmentResponseDTO(Department entity) {
        super(entity);
        this.organ = entity.getOrganId();
    }
}
