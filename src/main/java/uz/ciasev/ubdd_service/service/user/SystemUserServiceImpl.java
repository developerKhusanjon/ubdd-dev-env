package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.repository.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User)
                return (User) principal;
        }

        return null;
    }

    @Override
    public Optional<User> getCurrentUserOpt() {
        return Optional.ofNullable(getCurrentUser());
    }

    @Override
    public User getCurrentUserOrThrow() {
        User user = getCurrentUser();

        if (user == null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            log.error("Current user is null in url {}", request.getRequestURI());
            throw new ImplementationException(ErrorCode.USER_NOT_DEFINED, "Current user is null");
        }


        return user;
    }
}