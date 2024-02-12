package uz.ciasev.ubdd_service.config.security.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaUsernameLoginDTO {

    private String username;
    private String password;
    @JsonProperty("captcha_code")
    private String captchaCode;
}
