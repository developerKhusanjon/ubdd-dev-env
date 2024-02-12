package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class NotificationSmsTextProtocolRequestDTO implements Serializable {

    private String fio;
    private LocalDate birthDate;
    private String protocolSeries;
    private String protocolNumber;
    private LocalDate registrationDate;
    private String articleName;
    private String organName;
    private String districtName;
}
