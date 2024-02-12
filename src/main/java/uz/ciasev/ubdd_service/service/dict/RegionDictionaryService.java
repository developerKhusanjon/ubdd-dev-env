package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.request.RegionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.RegionResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.List;
import java.util.Optional;

public interface RegionDictionaryService extends DictionaryCRUDService<Region, RegionRequestDTO, RegionRequestDTO>, UnknownValueByIdDictionaryService<Region> {

    List<RegionResponseDTO> findAllMibPresence();

    Optional<Region> findByIdMibPresence(Long id);
    Optional<Region> findByIdCourtPresence(Long id);
}
