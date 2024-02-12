package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class ThirdCourtEvidenceRequestDTO {

    private Long evidenceId;
    private Long evidenceResult;
    @NotNull(message = "evidenceCourtId empty")
    private Long evidenceCourtId;
    private Long caseId;
    private String personDescription;
    private Long evidenceCategory;
    private String evidenceName;
    private Double evidenceCountAndUnity;
    private Long measureId;
    private Long currencyId;
    private Long amount;
}
