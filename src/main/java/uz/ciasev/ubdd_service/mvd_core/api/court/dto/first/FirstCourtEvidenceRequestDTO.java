package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import lombok.Data;

@Data
public class FirstCourtEvidenceRequestDTO {

    private Integer caseId;
    private String personDescription;
    private Integer evidenceId;
    private Integer evidenceCategory;
    private String evidenceName;
    private String evidenceCountAndUnity;
    private Integer measureId;
    private Integer currencyId;
    private Long amount;
}