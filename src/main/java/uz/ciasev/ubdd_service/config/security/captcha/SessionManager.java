package uz.ciasev.ubdd_service.config.security.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.WeakHashMap;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private static final WeakHashMap<String, HttpSession> sessionRepository = new WeakHashMap<>();

    public Optional<HttpSession> getSessionById(String sessionId) {
        return Optional.of(sessionRepository.get(sessionId));
    }

    public void save(HttpSession session) {
        sessionRepository.put(session.getId(), session);
    }

    public void delete(String sessionId) {
        sessionRepository.remove(sessionId);
    }

}