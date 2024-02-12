package uz.ciasev.ubdd_service.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.MessageLocalizationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.system.MessageLocalizationResponseDTO;

public interface MessageLocalizationService {

    MessageLocalizationResponseDTO update(Long id, MessageLocalizationRequestDTO messageLocalizationRequestDTO);
    Page<MessageLocalizationResponseDTO> findAll(Pageable pageable);
    MessageLocalizationResponseDTO findDTOById(Long id);
}
