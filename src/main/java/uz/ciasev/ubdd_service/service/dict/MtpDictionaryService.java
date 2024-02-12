package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.MtpCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MtpUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Mtp;

public interface MtpDictionaryService extends DictionaryCRUDService<Mtp, MtpCreateRequestDTO, MtpUpdateRequestDTO> {
}