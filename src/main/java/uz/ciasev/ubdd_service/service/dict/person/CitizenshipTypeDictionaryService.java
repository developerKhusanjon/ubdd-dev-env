package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByAliasDictionaryService;


public interface CitizenshipTypeDictionaryService extends SimpleBackendDictionaryService<CitizenshipType, CitizenshipTypeAlias>, UnknownValueByAliasDictionaryService<CitizenshipType, CitizenshipTypeAlias> {
}
