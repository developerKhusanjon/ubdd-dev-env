package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationSmsTextDigitalSignaturePasswordRequestDTO implements Serializable {

    private String serial;
    private String password;
    private LocalDateTime expiresOn;
}
