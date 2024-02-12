package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

@Data
public class MovementRequest {

    // Новый идентифиакатор текущего дела, полученый при передачи
    private Long claimId;

    // Суд в каторый передали дело
    private CourtTransfer court;

}
