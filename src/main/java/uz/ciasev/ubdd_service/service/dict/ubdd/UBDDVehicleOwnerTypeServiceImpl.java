package uz.ciasev.ubdd_service.service.dict.ubdd;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDVehicleOwnerTypeCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UBDDVehicleOwnerTypeUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd.UBDDVehicleOwnerTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDVehicleOwnerTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDVehicleOwnerTypeServiceImpl extends AbstractDictionaryCRUDService<UBDDVehicleOwnerType, UBDDVehicleOwnerTypeCreateRequestDTO, UBDDVehicleOwnerTypeUpdateRequestDTO>
        implements UBDDVehicleOwnerTypeService {

    private final Long unknownId = 2L;

    private final String subPath = "ubdd-vehicle-owner-type";
    private final TypeReference<UBDDVehicleOwnerTypeCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<UBDDVehicleOwnerTypeUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final UBDDVehicleOwnerTypeRepository repository;
    private final Class<UBDDVehicleOwnerType> entityClass = UBDDVehicleOwnerType.class;

    @Override
    public UBDDVehicleOwnerTypeResponseDTO buildResponseDTO(UBDDVehicleOwnerType entity) {
        return new UBDDVehicleOwnerTypeResponseDTO(entity);
    }
}
