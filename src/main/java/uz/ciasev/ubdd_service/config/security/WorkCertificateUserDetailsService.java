package uz.ciasev.ubdd_service.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.ciasev.ubdd_service.repository.user.UserRepository;


@RequiredArgsConstructor
public class WorkCertificateUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
