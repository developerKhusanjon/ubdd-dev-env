package uz.ciasev.ubdd_service.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.repository.user.UserRepository;

@Service
public class UsernameDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    public UsernameDaoAuthenticationProvider(UserRepository userRepository) {
        setUserDetailsService(new UserDetailsServiceImpl(userRepository::findByUsernameIgnoreCase));
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthenticationToken.class.equals(authentication);
    }
}
