package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DepartmentCacheDeserializer extends AbstractDictDeserializer<Department> {

    @Autowired
    public DepartmentCacheDeserializer(DictionaryService<Department> departmentService) {
        super(Department.class, departmentService);
    }
}