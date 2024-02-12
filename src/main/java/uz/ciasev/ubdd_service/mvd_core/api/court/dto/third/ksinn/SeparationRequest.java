package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeparationRequest {

    // Идентификатор нового дела, в каторое надо выделить нарушителей из текущего
    private Long claimId;

    // Идентификаторые выделяемых в новое дело нарушителей
    private List<Long> violatorsId;
}
