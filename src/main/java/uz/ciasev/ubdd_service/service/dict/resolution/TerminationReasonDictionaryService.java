package uz.ciasev.ubdd_service.service.dict.resolution;

import uz.ciasev.ubdd_service.dto.internal.dict.request.TerminationReasonRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

import java.util.Set;

public interface TerminationReasonDictionaryService extends DictionaryCRUDService<TerminationReason, TerminationReasonRequestDTO, TerminationReasonRequestDTO> {

    Set<Long> getParticipateOfRepeatabilityCached();
}
