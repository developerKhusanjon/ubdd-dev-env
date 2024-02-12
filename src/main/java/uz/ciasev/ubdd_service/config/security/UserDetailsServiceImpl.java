package uz.ciasev.ubdd_service.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import uz.ciasev.ubdd_service.config.security.exception.CiasevUsernameNotFoundException;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;
import java.util.function.Function;


@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Function<String, Optional<User>> findUser;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional;

        try {
            userOptional = findUser.apply(username);
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getCause() instanceof NonUniqueResultException) {
                throw new CiasevUsernameNotFoundException(ErrorCode.AUTH_ERROR_NON_UNIQUE_LOGIN, "System found more then one user by credential " + username);
            }
            throw new CiasevUsernameNotFoundException(ErrorCode.AUTH_ERROR_INCORRECT_RESULT_SIZE, "Fetching of user throws error " + e.getMessage());
        }
        catch (Exception e) {
            throw new CiasevUsernameNotFoundException(ErrorCode.AUTH_ERROR_IN_USER_FETCHING, "Fetching of user throws error " + e.getMessage());
        }

        return userOptional
                .orElseThrow(() -> new CiasevUsernameNotFoundException(ErrorCode.AUTH_ERROR_USER_NOT_FOUND, "Not found user by credential " + username));
    }
}
