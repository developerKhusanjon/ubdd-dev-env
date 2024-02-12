package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.DepartmentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DepartmentUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Department;

public interface DepartmentDictionaryService extends DictionaryCRUDService<Department, DepartmentCreateRequestDTO, DepartmentUpdateRequestDTO> {
}
