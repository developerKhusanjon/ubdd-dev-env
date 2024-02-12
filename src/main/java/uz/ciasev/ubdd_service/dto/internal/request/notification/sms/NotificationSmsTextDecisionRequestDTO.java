package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSmsTextDecisionRequestDTO implements Serializable {

    private String fio;
    private LocalDate birthDate;
    private Long penaltyAmount;
    private String penaltyInvoiceNumber;
    private LocalDate penaltyPaymentDate;
    private PenaltyPunishment.DiscountVersion penaltyDiscount;
    private LocalDate penaltyPaymentDateDiscount;
    private Long penaltyDiscountAmount;

    private String damageInvoiceNumber;
    private Long damageAmount;

    private Long victimDamageAmount;

    private String organName;
    private String regionName;
    private String districtName;
}
