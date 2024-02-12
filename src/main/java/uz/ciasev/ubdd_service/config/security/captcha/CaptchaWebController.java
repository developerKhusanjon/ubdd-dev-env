package uz.ciasev.ubdd_service.config.security.captcha;

import com.github.cage.GCage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CaptchaWebController {

    private static final int CAPTCHA_LENGTH = 5;
    private final GCage gCage;

    private final SessionManager sessionManager;
    private static final Random random = new Random();

    @SneakyThrows
    @GetMapping("auth/captcha/generate")
    public ResponseEntity<CaptchaResponseDTO> getCaptcha(HttpSession session) {
        String token = generate(CAPTCHA_LENGTH);
        session.setAttribute("captcha", token);

        sessionManager.save(session);

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        gCage.draw(token, outputStream);

        CaptchaResponseDTO dto = CaptchaResponseDTO.builder()
                .imageFormat("image/".concat(gCage.getFormat()))
                .captcha(new ByteArrayResource(outputStream.toByteArray()))
                .sessionId(session.getId())
                .build();

        return ResponseEntity.ok(dto);
    }

    private String generate(int captchaLength) {
        String captcha = "1234567890";
        StringBuilder captchaBuffer = new StringBuilder();

        while (captchaBuffer.length() < captchaLength) {
            int index = (int) (random.nextFloat() * captcha.length() - 1);
            captchaBuffer.append(captcha.charAt(index));
        }
        return captchaBuffer.toString();
    }
}
