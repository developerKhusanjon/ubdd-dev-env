package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTintingDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTintingResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTintingData;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UbddTintingDataService {

    UbddTintingData save(User user, UbddTintingDTO dto);

    UbddTintingResponseDTO saveAndGetDTO(User user, UbddTintingDTO dto);

    UbddTintingData findById(User user, Long id);

    UbddTintingResponseDTO findDTOById(User user, Long id);

    UbddTintingData update(User user, Long id, UbddTintingDTO dto);
}
