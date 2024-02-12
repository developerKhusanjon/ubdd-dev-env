package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.dto.internal.dict.request.OccupationCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.OccupationUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.service.dict.*;


public interface OccupationDictionaryService extends
        DictionaryCRUDService<Occupation, OccupationCreateRequestDTO, OccupationUpdateRequestDTO>,
        UnknownValueByIdDictionaryService<Occupation>, ExternalIdSavingDictionaryService<Occupation> {
}