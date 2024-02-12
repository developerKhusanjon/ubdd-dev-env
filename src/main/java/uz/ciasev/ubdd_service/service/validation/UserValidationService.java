package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.user.UserRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ApplicationException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface UserValidationService {

    void validate(User admin, Optional<Long> updatedUserId, UserRequestDTO userRequestDTO, Supplier<Optional<User>> duplicateUsers) throws ApplicationException;

    void validateRoleList(User admin, List<Role> roles);

    void validateUserUniqueness(Organ organ, String pinpp);

    void validateAdminAccessOnUser(User admin, User user);

    void checkAdministrationAllow(@Nullable Organ organ);
}
