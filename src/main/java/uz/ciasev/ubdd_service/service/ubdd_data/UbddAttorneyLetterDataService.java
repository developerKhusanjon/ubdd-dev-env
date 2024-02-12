package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddAttorneyLetterData;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UbddAttorneyLetterDataService {

    UbddAttorneyLetterData save(User user, UbddAttorneyLetterDTO dto);

    UbddAttorneyLetterResponseDTO saveAndGetDTO(User user, UbddAttorneyLetterDTO dto);

    UbddAttorneyLetterData findById(User user, Long id);

    UbddAttorneyLetterResponseDTO findDTOById(User user, Long id);
}
