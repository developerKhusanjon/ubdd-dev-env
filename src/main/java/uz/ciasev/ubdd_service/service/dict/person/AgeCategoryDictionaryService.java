package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.dto.internal.dict.request.AgeCategoryRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AgeCategoryDictionaryService extends DictionaryCRUDService<AgeCategory, AgeCategoryRequestDTO, AgeCategoryRequestDTO> {

    AgeCategory getByBirthdate(Boolean isViolatorOnly, LocalDate birthdate, LocalDateTime atTime);
}
