package uz.ciasev.ubdd_service.dto.internal.request.resolution.court;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidDecision;
import uz.ciasev.ubdd_service.utils.validator.ValidPunishment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ValidDecision
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CourtDecisionRequestDTO implements DecisionRequestDTO {

    @NotNull(message = ErrorCode.VIOLATOR_ID_REQUIRED)
    private Long violatorId;

    @NotNull(message = ErrorCode.DEFENDANT_ID_REQUIRED)
    private Long defendantId;

    @NotNull(message = "DECISION_TYPE_REQUIRED")
    private DecisionTypeAlias decisionType;

    private TerminationReason terminationReason;

    private LocalDate executionFromDate;

    @Valid
    @ValidPunishment(message = ErrorCode.MAIN_PUNISHMENT_INVALID)
    private CourtPunishmentRequestDTO mainPunishment;

    @Valid
    @ValidPunishment(message = ErrorCode.ADDITION_PUNISHMENT_INVALID)
    private CourtPunishmentRequestDTO additionPunishment;

    @Valid
    @Builder.Default
    private List<CourtCompensationRequestDTO> compensations = new ArrayList<>();

    private boolean isArticle33;

    private boolean isArticle34;

    @Override
    public Decision buildDecision() {
        Decision decision = new Decision();

        decision.setDecisionTypeAlias(this.getDecisionType());
        decision.setTerminationReason(this.getTerminationReason());
        decision.setExecutionFromDate(this.executionFromDate);
        decision.setArticle33(this.isArticle33);
        decision.setArticle34(this.isArticle34);
        decision.setDefendantId(this.defendantId);

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
    public LocalDate getExecutionFromDate() {
        return Optional.ofNullable(this.executionFromDate).orElseGet(LocalDate::now);
    }

    @Override
    public List<CourtCompensationRequestDTO> getCompensations() {
        return Optional.ofNullable(this.compensations).orElseGet(List::of);
    }
}
