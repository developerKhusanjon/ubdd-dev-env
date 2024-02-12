package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ThirdCourtCaseSeparationRequestDTO {

    // Идентификатор нового дела, в каторое надо выделить нарушителей из текущего
    private Long claimSeparationId;

    // Идентификаторые выделяемых в новое дело нарушителей
    private List<Long> caseSeparationViolatorId;
}
