package uz.ciasev.ubdd_service.service.system;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.MessageLocalizationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.system.MessageLocalizationResponseDTO;
import uz.ciasev.ubdd_service.entity.system.MessageLocalization;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.system.MessageLocalizationRepository;

@Service
@RequiredArgsConstructor
public class MessageLocalizationServiceImpl implements MessageLocalizationService {

    private final MessageLocalizationRepository messageLocalizationRepository;

    @Override
    public MessageLocalizationResponseDTO update(Long id, MessageLocalizationRequestDTO messageLocalizationRequestDTO) {
        MessageLocalization messageLocalization = findById(id);

        messageLocalization.setText(messageLocalizationRequestDTO.getText());

        return new MessageLocalizationResponseDTO(messageLocalizationRepository.save(messageLocalization));
    }

    @Override
    public Page<MessageLocalizationResponseDTO> findAll(Pageable pageable) {
        return messageLocalizationRepository
                .findAll(pageable)
                .map(MessageLocalizationResponseDTO::new);
    }

    @Override
    public MessageLocalizationResponseDTO findDTOById(Long id) {
        return new MessageLocalizationResponseDTO(findById(id));
    }

    private MessageLocalization findById(Long id) {
        return messageLocalizationRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(MessageLocalization.class, id));
    }
}
