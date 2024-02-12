package uz.ciasev.ubdd_service.service.dict.mib;

import uz.ciasev.ubdd_service.dto.internal.dict.request.MibCaseStatusCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MibCaseStatusUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface MibCaseStatusService extends DictionaryCRUDService<MibCaseStatus, MibCaseStatusCreateRequestDTO, MibCaseStatusUpdateRequestDTO> {

    MibCaseStatus findByCode(String code);
}
