package uz.ciasev.ubdd_service.service.dict.user;

import uz.ciasev.ubdd_service.dto.internal.dict.request.UserStatusRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface UserStatusService extends DictionaryCRUDService<UserStatus, UserStatusRequestDTO, UserStatusRequestDTO> {
}
