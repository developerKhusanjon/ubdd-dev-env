package uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtCompensationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.OrganPunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ManualMaterialDecisionRequestDTO implements DecisionRequestDTO {


    private Long violatorId;

    private DecisionTypeAlias decisionType;

    private TerminationReason terminationReason;

    private LocalDate executionFromDate;

    private OrganPunishmentRequestDTO mainPunishment;

    private Boolean isArticle33;

    @Override
    public Decision buildDecision() {
        Decision decision = new Decision();

        decision.setDecisionTypeAlias(this.getDecisionType());
        decision.setTerminationReason(this.getTerminationReason());
        decision.setExecutionFromDate(this.executionFromDate);
        decision.setArticle33(this.isArticle33);

        Optional.ofNullable(this.getMainPunishment())
                .map(PunishmentRequestDTO::buildPunishment)
                .map(p -> {
                    p.setMain(true);
                    return p;
                })
                .ifPresent(decision::setMainPunishment);

        Optional.ofNullable(this.getAdditionPunishment())
                .map(PunishmentRequestDTO::buildPunishment)
                .ifPresent(decision::setAdditionPunishment);

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
    public PunishmentRequestDTO getAdditionPunishment() {
        return null;
    }

}
