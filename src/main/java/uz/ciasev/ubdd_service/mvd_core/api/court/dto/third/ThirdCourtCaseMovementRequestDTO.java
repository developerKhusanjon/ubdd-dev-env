package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ThirdCourtCaseMovementRequestDTO {

    // Идентификатор дела, пересмотром каторого являеться тукуший запрос
    private Long claimReviewId;


    // Идентификатор дела, в каторое обьеденить текущее дело
    private Long claimMergeId;


    // Новый идентифиакатор текущего дела, полученый при передачи
    private Long otherCourtClaimId;

    // Суд в каторый передали дело
    private Long otherCourtId;

    // Выделения протоколов из текущегодела
    private List<ThirdCourtCaseSeparationRequestDTO> caseSeparation;

}
