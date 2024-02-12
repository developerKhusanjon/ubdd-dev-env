package uz.ciasev.ubdd_service.service.execution;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class BillingData {

    private boolean hasPayments;
    private Violator ownerViolator;
    private Optional<LocalDateTime> lastPayTime;
    private Optional<Long> totalAmount;
    private List<String> executorNames;


}
