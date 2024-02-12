package uz.ciasev.ubdd_service.service.dict.person;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.AgeCategoryRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.person.AgeCategoryResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamNotPresent;
import uz.ciasev.ubdd_service.repository.dict.person.AgeCategoryRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Getter
public class AgeCategoryDictionaryServiceImpl extends AbstractDictionaryCRUDService<AgeCategory, AgeCategoryRequestDTO, AgeCategoryRequestDTO>
        implements AgeCategoryDictionaryService {

    private final String subPath = "age-categories";
    private final TypeReference<AgeCategoryRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<AgeCategoryRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final AgeCategoryRepository repository;
    private final Class<AgeCategory> entityClass = AgeCategory.class;

    @Override
    public AgeCategory getByBirthdate(Boolean isViolatorOnly, LocalDate birthdate, LocalDateTime atTime) {
        int age = DateTimeUtils.getFullYearsBetween(birthdate, atTime.toLocalDate());
        return repository
                .findByFullYears(isViolatorOnly, age)
                .orElseThrow(() -> new EntityByParamNotPresent(AgeCategory.class, "ownerAlias", isViolatorOnly, "age", age));
    }

    @Override
    public AgeCategoryResponseDTO buildResponseDTO(AgeCategory ageCategory) {
        return new AgeCategoryResponseDTO(ageCategory);
    }

    @Override
    public AgeCategoryResponseDTO buildListResponseDTO(AgeCategory entity) {return new AgeCategoryResponseDTO(entity);}
}
