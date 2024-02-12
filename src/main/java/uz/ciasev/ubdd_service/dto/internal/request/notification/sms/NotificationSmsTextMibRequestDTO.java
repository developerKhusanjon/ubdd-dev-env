package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSmsTextMibRequestDTO implements Serializable {

    private String fio;
    private LocalDate birthDate;
    private LocalDate mastByExecutedBeforeDate;
    private Long paymentAmount;
    private LocalDate sendDateToMib;
}
