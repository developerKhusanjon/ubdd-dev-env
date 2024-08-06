package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDate;
import java.util.List;

public interface DecisionRequestDTO {

    Long getViolatorId();

    DecisionTypeAlias getDecisionType();

    LocalDate getExecutionFromDate();

    default TerminationReason getTerminationReason() {return null;}

    default CriminalCaseTransferResultType getCriminalCaseTransferResultType() {return null;}

    default PunishmentRequestDTO getMainPunishment() {return null;}

    default PunishmentRequestDTO getAdditionPunishment() {return null;}

    Decision buildDecision();

    ArticlePart getArticlePart();

    Boolean getIsJuridic();

}
