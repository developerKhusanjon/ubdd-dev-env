package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByIdDictionaryService;

public interface NationalityDictionaryService extends SimpleEmiDictionaryService<Nationality>, UnknownValueByIdDictionaryService<Nationality> {
}
