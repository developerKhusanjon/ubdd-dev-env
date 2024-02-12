package uz.ciasev.ubdd_service.service.dict.evidence;

import uz.ciasev.ubdd_service.dto.internal.dict.request.EvidenceCategoryCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EvidenceCategoryUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface EvidenceCategoryDictionaryService extends DictionaryCRUDService<EvidenceCategory, EvidenceCategoryCreateRequestDTO, EvidenceCategoryUpdateRequestDTO> {
}
