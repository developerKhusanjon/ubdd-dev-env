package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.DistrictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.DistrictUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DistrictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.List;
import java.util.Optional;

public interface DistrictDictionaryService extends DictionaryCRUDService<District, DistrictCreateRequestDTO, DistrictUpdateRequestDTO>, UnknownValueByIdDictionaryService<District> {
    List<DistrictResponseDTO> findAllMibPresence();
    List<DistrictResponseDTO> findAllCourtPresence();

    Optional<District> findByIdMibPresence(Long id);
    Optional<District> findByIdCourtPresence(Long id);

    List<District> findAllByRegion(Region region);
}
