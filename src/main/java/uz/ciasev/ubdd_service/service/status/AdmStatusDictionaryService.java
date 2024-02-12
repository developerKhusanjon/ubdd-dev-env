package uz.ciasev.ubdd_service.service.status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.dict.AdmStatusResponseDTO;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

public interface AdmStatusDictionaryService {

    AdmStatus getById(Long id);

    AdmStatus findByAlias(AdmStatusAlias registered);

    Page<AdmStatusResponseDTO> findAll(Pageable pageable);
}
