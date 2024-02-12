package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.court.CourtDeclineReasonsHistory;
import uz.ciasev.ubdd_service.utils.types.Ids;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtDeclineReasonsHistoryResponseDTO {

    private Long id;
    private Long caseId;
    private LocalDateTime createdTime;
    private Ids declinedReasons;

    public CourtDeclineReasonsHistoryResponseDTO(CourtDeclineReasonsHistory declineReasons) {
        this.id = declineReasons.getId();
        this.caseId = declineReasons.getCaseId();
        this.createdTime = declineReasons.getCreatedTime();
        this.declinedReasons = new Ids(declineReasons.getDeclinedReasons());
    }
}
