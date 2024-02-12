package uz.ciasev.ubdd_service.service.dict.court;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ViolationRepeatabilityStatusCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.ViolationRepeatabilityStatusUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.ViolationRepeatabilityStatusResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.repository.dict.court.ViolationRepeatabilityStatusRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class ViolationRepeatabilityStatusDictionaryServiceImpl extends AbstractDictionaryCRUDService<ViolationRepeatabilityStatus, ViolationRepeatabilityStatusCreateRequestDTO, ViolationRepeatabilityStatusUpdateRequestDTO>
        implements ViolationRepeatabilityStatusDictionaryService {

    private final String subPath = "violation-repeatability";
    private final TypeReference<ViolationRepeatabilityStatusCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<ViolationRepeatabilityStatusUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final ViolationRepeatabilityStatusRepository repository;
    private final Class<ViolationRepeatabilityStatus> entityClass = ViolationRepeatabilityStatus.class;

    @Override
    public ViolationRepeatabilityStatusResponseDTO buildResponseDTO(ViolationRepeatabilityStatus entity) {
        return new ViolationRepeatabilityStatusResponseDTO(entity);
    }
}
