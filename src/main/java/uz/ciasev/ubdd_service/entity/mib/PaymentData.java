package uz.ciasev.ubdd_service.entity.mib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentData implements Serializable {

    private String blankNumber;

    private LocalDate blankDate;

    private LocalDateTime paymentTime;

    private Long amount;

    private String fromBankCode;

    private String fromBankAccount;

    private String fromBankName;

    private String fromInn;

    private String toBankCode;

    private String toBankAccount;

    private String toBankName;

    private String toInn;
}