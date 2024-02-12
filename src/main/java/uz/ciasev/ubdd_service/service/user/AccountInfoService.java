package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.dto.internal.response.user.UserResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

public interface AccountInfoService {

    UserResponseDTO getMe(User user);
}
