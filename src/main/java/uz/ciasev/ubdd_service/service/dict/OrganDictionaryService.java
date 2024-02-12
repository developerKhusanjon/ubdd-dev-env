package uz.ciasev.ubdd_service.service.dict;


import uz.ciasev.ubdd_service.dto.internal.dict.request.OrganRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.OrganAlias;

public interface OrganDictionaryService extends AliasedDictionaryService<Organ, OrganAlias>, DictionaryCRUDService<Organ, OrganRequestDTO, OrganRequestDTO> {

    Organ getCourtOrgan();

    Organ getMibOrgan();

    Organ getMvdOrgan();
}