package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.annotation.Nullable;

public interface UbddTexPassDataService {

    UbddTexPassData save(@Nullable User user, UbddTexPassDTOI dto);

    UbddTexPassResponseDTO saveAndGetDTO(User user, UbddTexPassDTO dto);

    UbddTexPassData getById(@Nullable User user, Long id);

    UbddTexPassResponseDTO getDTOById(User user, Long id);

    UbddTexPassData update(User user, Long id, UbddTexPassDTO dto);
}
