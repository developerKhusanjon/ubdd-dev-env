package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DepartmentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DepartmentUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DepartmentResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.repository.dict.DepartmentRepository;
import uz.ciasev.ubdd_service.service.settings.OrganInfoAutoCreateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class DepartmentDictionaryServiceImpl extends AbstractDictionaryCRUDService<Department, DepartmentCreateRequestDTO, DepartmentUpdateRequestDTO>
        implements DepartmentDictionaryService {

    private final String subPath = "departments";
    private final TypeReference<DepartmentCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<DepartmentUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final Class<Department> entityClass = Department.class;
    private final DepartmentRepository repository;

    private  final OrganInfoAutoCreateService organInfoService;

    @Override
    @Transactional(timeout = 120)
    public List<Department> create(List<DepartmentCreateRequestDTO> request) {
        List<Department> departments = super.create(request);
        departments.stream().map(organInfoService::createForNew).collect(Collectors.toList());

        return departments;
    }

    @Override
    @Transactional(timeout = 120)
    public Department create(DepartmentCreateRequestDTO request) {
        Department department = super.create(request);
        organInfoService.createForNew(department);

        return department;
    }

    public DepartmentResponseDTO buildResponseDTO(Department department) {
        return new DepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO buildListResponseDTO(Department entity) {return new DepartmentResponseDTO(entity);}

}
