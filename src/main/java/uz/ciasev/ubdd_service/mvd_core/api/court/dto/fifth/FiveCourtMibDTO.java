package uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class FiveCourtMibDTO implements CourtBaseDTO {

    @NotNull(message = "caseId field can't be null")
    private Long caseId;

    @NotNull(message = "claimId field can't be null")
    private Long claimId;

    @Valid
    @NotNull(message = "performanceList can't be empty")
    List<FiveCourtPerformancesRequestDTO> performanceList;
}
