package uz.ciasev.ubdd_service.config.security.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import uz.ciasev.ubdd_service.utils.serializer.ByteArrayResourceSerializer;

@Data
@Builder
@AllArgsConstructor
public class CaptchaResponseDTO {

    @JsonProperty("captcha_format")
    private String imageFormat;
    @JsonSerialize(using = ByteArrayResourceSerializer.class)
    private ByteArrayResource captcha;
    @JsonProperty("session_id")
    private String sessionId;
}
