package uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FourthCourtPaymentDTO {

    private Long caseId;

    private Long claimId;

    @JsonIgnore
    private Long courtExecutionPaymentId;

    private List<FourthCourtDefendantDTO> defendant;
}
