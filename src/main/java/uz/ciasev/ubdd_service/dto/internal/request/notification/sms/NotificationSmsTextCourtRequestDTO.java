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
public class NotificationSmsTextCourtRequestDTO implements Serializable {

    private String fio;
    private LocalDate birthDate;
    private String courtRegionName;
    private String courtDistrictName;
    private String regNumber;
}
