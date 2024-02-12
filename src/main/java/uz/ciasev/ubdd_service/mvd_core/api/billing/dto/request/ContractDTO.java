package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    private final String note = "";

    private Long id;
    private String number;
    private Double amount;
    private String currency;

    private Long payerId;
    private Long departmentId;
    private LocalDate issueDate;

    private String payerName;
    private String departmentName;
}
