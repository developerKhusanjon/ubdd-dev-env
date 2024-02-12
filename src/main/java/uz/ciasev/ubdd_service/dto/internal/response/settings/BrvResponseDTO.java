package uz.ciasev.ubdd_service.dto.internal.response.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.settings.Brv;

import java.time.LocalDate;

@Getter
public class BrvResponseDTO {
    private final Long id;
    private final Long amount;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public BrvResponseDTO(Brv entity) {
        this.id = entity.getId();
        this.amount = entity.getAmount();
        this.fromDate = entity.getFromDate();
        this.toDate = entity.getToDate();
    }
}
