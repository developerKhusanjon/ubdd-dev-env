package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class AdmCaseConsiderRequestDTO {

    private LocalDateTime consideredTime;

    public LocalDateTime getConsideredTime() {
        return Optional.ofNullable(consideredTime).orElseGet(LocalDateTime::now);
    }
}
