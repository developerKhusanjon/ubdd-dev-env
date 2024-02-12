package uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.CompensationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDate;
import java.util.List;

@Data
public class CriminalCaseDecisionRequestDTO implements DecisionRequestDTO {

    private Long violatorId;

    private DecisionTypeAlias decisionType;

    private CriminalCaseTransferResultType criminalCaseTransferResultType;

    private LocalDate executionFromDate;

    @Override
    public Decision buildDecision() {
        Decision decision = new Decision();

        decision.setDecisionTypeAlias(decisionType);
        decision.setCriminalCaseTransferResultType(criminalCaseTransferResultType);
        decision.setExecutionFromDate(executionFromDate);

        return decision;
    }

    @Override
    public ArticlePart getArticlePart() {
        return null;
    }

    @Override
    public Boolean getIsJuridic() {
        return null;
    }

    @Override
    public List<? extends CompensationRequestDTO> getCompensations() {
        return List.of();
    }
}
