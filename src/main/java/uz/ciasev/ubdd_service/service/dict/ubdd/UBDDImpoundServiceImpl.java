package uz.ciasev.ubdd_service.service.dict.ubdd;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDImpoundCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDImpoundUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd.UBDDImpoundResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDImpound;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDImpoundRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDImpoundServiceImpl extends AbstractDictionaryCRUDService<UBDDImpound, UBDDImpoundCreateRequestDTO, UBDDImpoundUpdateRequestDTO>
        implements UBDDImpoundService {

    private final String subPath = "ubdd-impound";
    private final TypeReference<UBDDImpoundCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<UBDDImpoundUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final UBDDImpoundRepository repository;
    private final Class<UBDDImpound> entityClass = UBDDImpound.class;

    public UBDDImpoundResponseDTO buildResponseDTO(UBDDImpound entity) {
        return new UBDDImpoundResponseDTO(entity);
    }
}
