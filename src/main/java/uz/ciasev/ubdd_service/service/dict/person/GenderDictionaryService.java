package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByIdDictionaryService;

public interface GenderDictionaryService extends SimpleEmiDictionaryService<Gender>, UnknownValueByIdDictionaryService<Gender> {
}