package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface SystemUserService {

    User getCurrentUser();
    Optional<User> getCurrentUserOpt();
    User getCurrentUserOrThrow();
}
