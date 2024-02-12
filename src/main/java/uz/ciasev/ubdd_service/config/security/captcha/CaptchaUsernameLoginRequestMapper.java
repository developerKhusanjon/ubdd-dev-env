package uz.ciasev.ubdd_service.config.security.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import uz.ciasev.ubdd_service.config.security.UsernameAuthenticationToken;

@RequiredArgsConstructor
public class CaptchaUsernameLoginRequestMapper implements uz.ciasev.ubdd_service.config.security.captcha.CaptchaLoginRequestMapper<CaptchaUsernameLoginDTO> {

    private final SessionManager sessionManager;

    @Override
    public Authentication build(CaptchaUsernameLoginDTO body) {
        return new UsernameAuthenticationToken(body.getUsername(), body.getPassword());
    }

    @Override
    public Class getBodyClass() {
        return CaptchaUsernameLoginDTO.class;
    }

    @Override
    public boolean validateCredentials(CaptchaUsernameLoginDTO body) {
        return body != null
                && body.getUsername() != null
                && body.getPassword() != null
                && body.getCaptchaCode() != null
                && !body.getUsername().isEmpty()
                && !body.getCaptchaCode().isEmpty();
    }

    public boolean validateCredentials(CaptchaUsernameLoginDTO body, String sessionId) {
        return validateCredentials(body)
                && sessionId != null
                && checkSessionCaptchaAttributeValid(sessionId, body.getCaptchaCode());
    }

    private boolean checkSessionCaptchaAttributeValid(String sessionId, String captchaCode) {
        boolean captchaIsValid = sessionManager.getSessionById(sessionId)
                .map(session -> session.getAttribute("captcha").equals(captchaCode)).orElse(false);
        if (captchaIsValid) {
            sessionManager.delete(sessionId);
        }
        return captchaIsValid;
    }

    @Override
    public String loginType() {
        return "username";
    }

}
