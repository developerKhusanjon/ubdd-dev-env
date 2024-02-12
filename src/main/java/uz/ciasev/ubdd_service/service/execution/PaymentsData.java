package uz.ciasev.ubdd_service.service.execution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PaymentsData {

    private LocalDateTime lastPayTime;
    private Long totalAmount;
    private List<String> executorNames;
}
